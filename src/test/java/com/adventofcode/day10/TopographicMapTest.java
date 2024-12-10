package com.adventofcode.day10;

import com.adventofcode.common.IntMatrix;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class TopographicMapTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void sumOfTrailheadsScoresIsValid(String description, IntMatrix matrix, long expected) {
        // given
        TopographicMap map = new TopographicMap();

        // when
        long sum = map.sumTrailheadsScores(matrix);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void sumOfTrailheadsRatingsIsValid(String description, IntMatrix matrix, long expected) {
        // given
        TopographicMap map = new TopographicMap();

        // when
        long sum = map.sumTrailheadsRatings(matrix);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", new IntMatrix(readFile("/day10/0.in")), 36),
                Arguments.of("1.in", new IntMatrix(readFile("/day10/1.in")), 652)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", new IntMatrix(readFile("/day10/0.in")), 81),
                Arguments.of("1.in", new IntMatrix(readFile("/day10/1.in")), 1432)
        );
    }
}