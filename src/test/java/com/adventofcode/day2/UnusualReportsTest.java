package com.adventofcode.day2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getIntRows;
import static org.assertj.core.api.Assertions.assertThat;

class UnusualReportsTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void countSafeReports(String description, List<List<Integer>> reports, long expected) {
        // given
        UnusualReports unusualReports = new UnusualReports();

        // when
        long sum = unusualReports.countSafe(reports);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void countSafeReportsWithProblemDampener(String description, List<List<Integer>> reports, long expected) {
        // given
        UnusualReports unusualReports = new UnusualReports();

        // when
        long sum = unusualReports.countSafeWithProblemDampener(reports);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", getIntRows("/day2/0.in"), 2),
                Arguments.of("1.in", getIntRows("/day2/1.in"), 279)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", getIntRows("/day2/0.in"), 4),
                Arguments.of("1.in", getIntRows("/day2/1.in"), 343)
        );
    }
}