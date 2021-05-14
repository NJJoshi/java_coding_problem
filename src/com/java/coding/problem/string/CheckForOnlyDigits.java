package com.java.coding.problem.string;

public class CheckForOnlyDigits {
    public static void main(String[] args) {
        System.out.println(containsOnlyDigits("12"));
        System.out.println(containsOnlyDigitsUsingLambda("12"));
        System.out.println(containsOnlyDigitsRegex("12"));
    }

    private static boolean containsOnlyDigits(String s) {
        for (char c : s.toCharArray()) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean containsOnlyDigitsUsingLambda(String s) {
        return
                !s.chars()
                .anyMatch( n -> !Character.isDigit(n));
    }

    public static boolean containsOnlyDigitsRegex(String str) {

        return str.matches("[0-9]+");
    }
}
