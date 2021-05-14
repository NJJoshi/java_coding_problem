package com.java.coding.problem.string;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FindFirstNonRepeatableCharacter {
    public static void main(String[] args) {
        String str = "MINIM";
        System.out.println(findFirstNonRepeatableCharacter(str));
        System.out.println(findFirstNonRepeatableCharacterUsingLambda(str));
    }

    /**
     * This method will going to find first non repeatable character present into string.
     *
     * @param str Input string from which we want to find non repeatable character.
     * @return first non-repeatable character is returned
     */
    private static char findFirstNonRepeatableCharacter(String str) {
        Map<Character, Integer> chars = new LinkedHashMap<>();

        for (char c : str.toCharArray()) {
            chars.compute(c, (k, v) -> (v == null) ? 1 : ++v);
        }

        for (Map.Entry<Character, Integer> entrySet : chars.entrySet()) {
            if (entrySet.getValue() == 1) {
                return entrySet.getKey();
            }
        }
        return Character.MIN_VALUE;
    }

    /**
     * This method will going to find first non repeatable character (including unicode)
     * present into string using lambda expression.
     *
     * @param str Input string from which we want to find non repeatable character.
     * @return first non-repeatable character is returned
     */
    private static String findFirstNonRepeatableCharacterUsingLambda(String str) {
        Map<Integer, Long> chs = str.codePoints()
                .mapToObj(cp -> cp)
                .collect(Collectors.groupingBy(c -> c, LinkedHashMap::new, Collectors.counting()));

        int cp = chs.entrySet()
                .stream()
                .filter(c -> c.getValue() == 1L)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(Integer.valueOf(Character.MIN_VALUE));
        return String.valueOf(Character.toChars(cp));
    }
}
