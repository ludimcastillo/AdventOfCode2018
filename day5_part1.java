package com.workday.transactions.rest.runtime;

import java.io.File;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO: add description
 *
 * @author ludim.castillo
 * @since Dec-2018
 */
public class Main {

    // Java Program to illustrate reading from Text File
    // using Scanner Class
    public static void main(String[] args)
	throws Exception {
        File file = new File("/Users/ludim.castillo/Downloads/input.txt");
        Scanner sc = new Scanner(file);

        String polymer = "";
        while (sc.hasNextLine()) {
            polymer = sc.nextLine();
            polymer = getFinalReactionfromPolymer(polymer);
        }
        System.out.println();
        System.out.println("Result => " + polymer);
        System.out.println("Length => " + polymer.length());
    }

    private static String getFinalReactionfromPolymer(String polymer) {
        for (int i = 0; i < polymer.length()-1; i++) {
            char ch1 = polymer.charAt(i);
            char ch2 = polymer.charAt(i+1);
            if (isInverseCaseOfEachOther(ch1, ch2)) {
                System.out.println("Before => " + polymer);
                polymer = polymer.substring(0, i) + polymer.substring(i+2);
                System.out.println("After => " + polymer);
                i = -1;
            }
        }
        return polymer;
    }

    private static boolean isInverseCaseOfEachOther(Character ch1, Character ch2) {
        if (Character.isLowerCase(ch1)) {
            return Character.isUpperCase(ch2) && ch2 == Character.toUpperCase(ch1);
        }
        else {
            return Character.isLowerCase(ch2) && ch1 == Character.toUpperCase(ch2);
        }
    }
}
