package com.java.coding.problem.string;

import sun.tools.tree.CharExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CountDuplicateCharacters1 {
    public static void main(String[] args) {
        String str = "Nirav Joshi";
        String strWithUTF32 = "Nirav Joshi \uD83D\uDC95";
        String str1 = String.valueOf(Character.toChars(128149));

        System.out.println(countDuplicateCharacters(str).toString());
        System.out.println(countDuplicateCharactersUsingLambda(str).toString());

        System.out.println(countDuplicateCharactersUTF(strWithUTF32));
        System.out.println(countDuplicateCharactersUTFUsingLambda(strWithUTF32));
    }

    private static Map<Character, Integer> countDuplicateCharacters(String str) {
        Map<Character, Integer> result = new HashMap<>();
        for(char c : str.toCharArray()) {
            result.compute(c, (k,v) -> (v == null) ? 1 : ++v);
        }
        return result;
    }

    private static Map<Character, Long> countDuplicateCharactersUsingLambda(String str) {
        Map<Character, Long> result = str.chars()
           .mapToObj(c -> (char) c)
           .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return result;
    }

    private static Map<String, Integer> countDuplicateCharactersUTF(String str) {
        Map<String, Integer> result = new HashMap<>();
        for(int i = 0 ; i < str.length(); i ++) {
            String ch = String.valueOf(Character.toChars(str.codePointAt(i)));
            if(i < str.length() - 1 && str.codePointCount(i, i+2) == 1) {
                i++;
            }

//            int cp = str.codePointAt(i);
//            String ch1 = String.valueOf(Character.toChars(cp));
//            if(Character.charCount(cp) == 2) {
//                i++;
//            }
            result.compute(ch, (k, v) -> (v == null) ? 1 : ++v);
        }
        return result;
    }

    private static Map<String, Long> countDuplicateCharactersUTFUsingLambda(String str) {
        Map<String, Long> result = str.codePoints()
                                      .mapToObj(ch -> String.valueOf(Character.toChars(ch)))
                                      .collect(Collectors.groupingBy(ch -> ch, Collectors.counting()));
        return result ;
    }
}
