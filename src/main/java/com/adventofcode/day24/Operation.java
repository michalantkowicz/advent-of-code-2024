package com.adventofcode.day24;

import java.util.function.BiFunction;

enum Operation {
    AND((a, b) -> a & b), OR((a, b) -> a | b), XOR((a, b) -> a ^ b);

    private final BiFunction<Integer, Integer, Integer> operation;

    Operation(BiFunction<Integer, Integer, Integer> operation) {
        this.operation = operation;
    }

    static Operation of(String string) {
        return switch (string) {
            case "AND" -> AND;
            case "OR" -> OR;
            case "XOR" -> XOR;
            default -> throw new IllegalArgumentException("Unrecognized string: " + string);
        };
    }

    Integer compute(int a, int b) {
        return operation.apply(a, b);
    }
}
