package com.adventofcode.day7;

import java.util.List;
import java.util.function.BiFunction;

import static java.lang.Long.parseLong;

class Calculator {
    long sumValidEquations(List<List<Long>> equations) {
        List<BiFunction<Long, Long, Long>> operators = List.of((a, b) -> a * b, Long::sum);
        return calculateSumOfValid(equations, operators);
    }

    long sumValidEquationsWithConcatenations(List<List<Long>> equations) {
        List<BiFunction<Long, Long, Long>> operators = List.of((a, b) -> a * b, Long::sum, (a, b) -> parseLong(a + String.valueOf(b)));
        return calculateSumOfValid(equations, operators);
    }

    private long calculateSumOfValid(List<List<Long>> equations, List<BiFunction<Long, Long, Long>> operators) {
        return equations.stream()
                .filter(e -> isValid(e, 2, e.get(1), e.getFirst(), operators))
                .mapToLong(List::getFirst)
                .sum();
    }

    private boolean isValid(List<Long> numbers, int position, long result, long expected, List<BiFunction<Long, Long, Long>> operators) {
        if (position == numbers.size()) {
            return (result == expected);
        }
        return operators.stream().anyMatch(o -> isValid(numbers, position + 1, o.apply(result, numbers.get(position)), expected, operators));
    }
}
