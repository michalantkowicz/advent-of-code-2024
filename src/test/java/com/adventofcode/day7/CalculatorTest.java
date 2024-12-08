package com.adventofcode.day7;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getColumn;
import static com.adventofcode.TestUtils.getLongColumn;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void checkCalibrationResult(String description, List<List<Long>> equations, long expected) {
        // given
        Calculator calculator = new Calculator();

        // when
        long result = calculator.sumValidEquations(equations);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Disabled("Running longer than 1s, run manually if necessary")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void checkCalibrationResultWithConcatenations(String description, List<List<Long>> equations, long expected) {
        // given
        Calculator calculator = new Calculator();

        // when
        long result = calculator.sumValidEquationsWithConcatenations(equations);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", parseEquations("/day7/0.in"), 3749),
                Arguments.of("1.in", parseEquations("/day7/1.in"), 1038838357795L)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", parseEquations("/day7/0.in"), 11387),
                Arguments.of("1.in", parseEquations("/day7/1.in"), 254136560217241L)
        );
    }

    private static List<List<Long>> parseEquations(String path) {
        List<List<Long>> equations = new ArrayList<>();
        List<Long> testValues = getLongColumn(path, 0, ":");
        List<List<Long>> numbers = getColumn(path, 1, ":").stream()
                .map(n -> stream(n.trim().split(" ")).map(Long::valueOf).toList())
                .toList();
        for (int i = 0; i < numbers.size(); i++) {
            List<Long> equation = new ArrayList<>();
            equation.add(testValues.get(i));
            equation.addAll(numbers.get(i));
            equations.add(equation);
        }
        return equations;
    }
}