package com.workday.transactions.rest.runtime;

import it.unimi.dsi.fastutil.Hash;

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

    private static HashMap<Character, Integer> unitTypeReactions = new HashMap<>();

    // Java Program to illustrate reading from Text File
    // using Scanner Class
    public static void main(String[] args)
	throws Exception {
        File file = new File("/Users/ludim.castillo/Downloads/input.txt");
        Scanner sc = new Scanner(file);

        String polymer = "";
        while (sc.hasNextLine()) {
            polymer = sc.nextLine();
            analyzePolymer(polymer);
        }

        //printReactionsMap();
        System.out.println(getShortestPolymer());
    }

    private static void printReactionsMap() {
        for (Object o : unitTypeReactions.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    private static void analyzePolymer(String polymer) {
        for (int i = 0; i < polymer.length()-1; i++) {
            char ch = polymer.charAt(i);
            char chInverse = getInverseCaseUnit(ch);
            if (!unitTypeReactions.containsKey(ch) && !unitTypeReactions.containsKey(getInverseCaseUnit(ch))) {
                int lengthNewPolymer = getFinalReactionfromPolymer(getPolymerWithoutUnits(polymer, ch)).length();
                unitTypeReactions.put(ch, lengthNewPolymer);
            }


        }
    }

    private static int getShortestPolymer() {
        int shortestLength = 10000;
        for (Object o : unitTypeReactions.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            int val = Integer.valueOf(pair.getValue().toString());
            if (val < shortestLength) {
                shortestLength = val;
            }
        }
        return shortestLength;
    }

    private static String getPolymerWithoutUnits(String polymer, Character unit) {
        //Derive inverse case of unit and replace that as well
        Character inverseOfUnit = getInverseCaseUnit(unit);
        return polymer.replaceAll(inverseOfUnit.toString(), "").replaceAll(unit.toString(), "");
    }

    private static Character getInverseCaseUnit(Character unit) {
        if (Character.isLowerCase(unit)) {
            return Character.toUpperCase(unit);
        }
        return Character.toLowerCase(unit);
    }

    private static String getFinalReactionfromPolymer(String polymer) {
        for (int i = 0; i < polymer.length()-1; i++) {
            char ch1 = polymer.charAt(i);
            char ch2 = polymer.charAt(i+1);
            if (isInverseCaseOfEachOther(ch1, ch2)) {
                polymer = polymer.substring(0, i) + polymer.substring(i+2);
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
