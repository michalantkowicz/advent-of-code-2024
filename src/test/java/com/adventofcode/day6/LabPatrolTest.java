package com.adventofcode.day6;

import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class LabPatrolTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void distanceIsValid(String description, String input, long expected) {
        // given
        LabPatrol labPatrol = new LabPatrol();
        StringMatrix matrix = new StringMatrix(input);
        Pair<Integer> start = matrix.streamIndices().filter(i -> "^".equals(matrix.at(i.a(), i.b()))).findFirst().orElseThrow();

        // when
        long sum = labPatrol.calculatePatrolDistance(matrix, start);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    @Disabled("Running longer than 1s, run manually if necessary")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void obstructionsCountIsValid(String description, String input, long expected) {
        // given
        LabPatrol labPatrol = new LabPatrol();
        StringMatrix matrix = new StringMatrix(input);
        Pair<Integer> start = matrix.streamIndices().filter(i -> "^".equals(matrix.at(i.a(), i.b()))).findFirst().orElseThrow();

        // when
        long sum = labPatrol.checkObstructions(matrix, start);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day6/0.in"), 41),
                Arguments.of("1.in", readFile("/day6/1.in"), 5086)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day6/0.in"), 6),
                Arguments.of("1.in", readFile("/day6/1.in"), 1770)
        );
    }
}