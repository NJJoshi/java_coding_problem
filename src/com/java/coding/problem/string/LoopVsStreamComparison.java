package com.java.coding.problem.string;

import java.util.ArrayList;
import java.util.List;

public class LoopVsStreamComparison {

    private static List<Integer> numbers = new ArrayList<>();
    static {
        for (int i = 1; i <= 50_000_000; i++) {
            numbers.add(i);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting up JVM warm up");
        for(int i = 0; i< 5; i ++) {
            forLoop();
            stream();
            parellelStream();
        }
        System.out.println("Ending up JVM warm up");

        long startTime = System.currentTimeMillis();
        long sum = forLoop();
        long endTime = System.currentTimeMillis();
        System.out.println("For calculating sum : " + sum + " loop time: " + (endTime - startTime) + "ms" );

        startTime = System.currentTimeMillis();
        sum = stream();
        endTime = System.currentTimeMillis();
        System.out.println("For calculating sum : " + sum + " stream loop time: " + (endTime - startTime) + "ms" );

        startTime = System.currentTimeMillis();
        sum = parellelStream();
        endTime = System.currentTimeMillis();

        System.out.println("For calculating sum : " + sum + " parellelStream loop time: " + (endTime - startTime) + "ms" );
    }

    private  static long forLoop(){
        long sum=0;
        for( int i : numbers ){
            sum += i;
        }
        return sum;
    }

    private static long stream(){
        return numbers.stream().mapToLong(Integer::longValue).sum();
    }

    private static long parellelStream(){
        return numbers.parallelStream().mapToLong(Integer::longValue).sum();
    }
}
