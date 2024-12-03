package com.adventofcode.day3;

import java.util.stream.Stream;

class CorruptedProgram {
    long sumProperMultiplications(String input) {
        long result = 0;
        String[] arguments = input.split("mul\\(");
        for (int i = 1; i < arguments.length; i++) {
            result += parseArgumentsAndTryMultiply(arguments[i]);
        }
        return result;
    }

    long sumMultiplicationsIgnoringDonts(String input) {
        long totalResult = sumProperMultiplications(input);
        long ignoredSum = sumIgnoredMultiplications(input.split("don't\\(\\)"));
        return totalResult - ignoredSum;
    }

    private long sumIgnoredMultiplications(String[] split) {
        long result = 0;
        for(int i = 1; i < split.length; i++) {
            result += Stream.of(split[i].split("do\\(\\)")).limit(1).mapToLong(this::sumProperMultiplications).sum();
        }
        return result;
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
}
