package com.java.coding.problem.string;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReversingString {
    public static void main(String[] args) {
        System.out.println(reverseWord("Nirav Joshi"));
        System.out.println(reverseStringUsingLambda("Nirav Joshi"));
    }

    private static String reverseWord(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    private static String reverseStringUsingLambda(String s) {
        Pattern PATTERN = Pattern.compile(" +");
        return PATTERN.splitAsStream(s)
                .map(word -> new StringBuilder(word).reverse())
                .collect(Collectors.joining(" "));
    }
}
