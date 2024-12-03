package com.adventofcode.day3;

import java.util.List;
import java.util.stream.Stream;

class CorruptedProgram {
    long sumProperMultiplications(String input) {
        return splitAndSkipFirst(input, "mul\\(").stream()
                .mapToInt(CorruptedProgram::parseArgumentsAndTryMultiply)
                .sum();
    }

    long sumMultiplicationsIgnoringDonts(String input) {
        long totalResult = sumProperMultiplications(input);
        long ignoredSum = splitAndSkipFirst(input, "don't\\(\\)").stream()
                .mapToLong(x -> sumProperMultiplications(x.split("do\\(\\)")[0]))
                .sum();
        return totalResult - ignoredSum;
    }

    private static int parseArgumentsAndTryMultiply(String arguments) {
        try {
            String expectedPair = arguments.split("\\)")[0];
            String expectedFirst = expectedPair.split(",")[0];
            String expectedSecond = expectedPair.split(",")[1];
            if (expectedFirst.length() > 3 || expectedSecond.length() > 3) {
                return 0;
            }
            return Integer.parseInt(expectedFirst) * Integer.parseInt(expectedSecond);
        } catch (Exception e) {
            return 0;
        }
    }

    private static List<String> splitAndSkipFirst(String string, String splitBy) {
        return Stream.of(string.split(splitBy)).skip(1).toList();
    }
}
