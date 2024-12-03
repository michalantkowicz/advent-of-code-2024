package com.adventofcode.day3;

class CorruptedProgram {
    long sumProperMultiplications(String input) {
        long result = 0;
        String[] arguments = input.split("mul\\(");
        for (int i = 1; i < arguments.length; i++) {
            result += parseArgumentsAndTryMultiply(arguments[i]);
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
