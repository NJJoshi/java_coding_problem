package com.java.coding.problem.string;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class GathererDemo {
    public static void main() {
        /*
          Gatherers.fold -> collapse elements into single result.
          Gatherers.scan(seed, op) -> running total/accumulation (like reduce)
          Gatherers.windowFixed(size) -> split the stream into fixed batch sizes.
          Gatherers.windowSliding(size) -> create overlapping sliding window.
          Gatherers.mapConcurrent(size) -> run mapping functions concurrently. for IO/Network related tasks
         */

        //Use of Gatherers.fold -> collapse elements into single result.
        List<Integer> numbers = List.of(11, 201, 39, 143, 35);

        Integer total = numbers.stream().reduce(0, Integer::sum);
        System.out.println("Using stream, total = " + total);

        Integer totalSum = numbers.stream()
                            .gather(Gatherers.fold(() -> 0, Integer::sum))
                            .findFirst().orElse(0);
        System.out.println("Using gatherers, totalSum = " + totalSum);

        // Use of Gatherers.scan(seed, op) -> running total/accumulation
        List<Integer> transactions = List.of(1100, -201, -39, 143, -35);
        AtomicInteger runningTotal = new AtomicInteger(0);
        List<Integer> balanceHistory = transactions.stream()
                .map(runningTotal::addAndGet)
                .toList();
        System.out.println("Using stream, balanceHistory = " + balanceHistory);

        List<Integer> balanceHist =  transactions.stream()
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();
        System.out.println("Using gatherers, balanceHist = " + balanceHist);

        //Use of Gatherers.windowFixed(size) -> split the stream into fixed batch sizes.
        List<Integer> orders = List.of(101,102,103,104,105,106,107, 108);
        int batchSize = 3;
        List<List<Integer>> batches = IntStream.range(0, batchSize)
                .mapToObj( i -> orders.subList(i * batchSize , Math.min((i + 1)*batchSize, orders.size())))
                .toList();
        System.out.println("Using stream, with batchSize = " + batchSize + " batches = " + batches);

        List<List<Integer>> batchData = orders.stream()
                .gather(Gatherers.windowFixed(3))
                .toList();
        System.out.println("Using gatherers, with batchSize = " + batchSize + " batchData = " + batchData);

        //Use of Gatherers.windowSliding(size) -> create overlapping sliding window.
        List<Integer> input = List.of(101,102,103,104,105);
        int windowSize = 3;
        List<List<Integer>> data = IntStream.range(0, input.size() - windowSize + 1)
                .mapToObj(i -> input.subList(i, i + windowSize)).toList();
        System.out.println("Using stream, with windowSize = " + windowSize + " data = " + data);

        List<List<Integer>> resultData = input.stream().gather(Gatherers.windowSliding(windowSize)).toList();
        System.out.println("Using Gatherers, with windowSize = " + windowSize + " resultData = " + resultData);

        //Use of Gatherers.mapConcurrent(size) -> run mapping functions concurrently. for IO/Network related tasks
        List<Integer> userIds = IntStream.rangeClosed(1,10).boxed().toList();

        List<CompletableFuture<String>> futures = userIds.stream()
                .map( id -> CompletableFuture.supplyAsync( () -> fetchUserProfile(id))).toList();

        List<String> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();
        System.out.println("Using stream");
        for (String result : results) {
            System.out.println(result);
        }
        System.out.println("Using Gatherers");

        userIds.stream()
                .gather(Gatherers.mapConcurrent(3, GathererDemo::fetchUserProfile))
                .forEach(System.out::println);
    }

    private static String fetchUserProfile(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return "Processed UserProfile: " + id;
    }
}
