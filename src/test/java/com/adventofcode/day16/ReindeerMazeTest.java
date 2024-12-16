package com.adventofcode.day16;

import com.adventofcode.common.StringMatrix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;

class ReindeerMazeTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperCostOfTheBestPath(String description, StringMatrix map, long expected) {
        // given
        ReindeerMaze maze = new ReindeerMaze();

        // when
        long cost = maze.getBestPathCost(map);

        // then
        Assertions.assertThat(cost).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperCountOfTheBestPath(String description, StringMatrix map, long expected) {
        // given
        ReindeerMaze maze = new ReindeerMaze();

        // when
        long cost = maze.getBestPathTilesCount(map);

        // then
        Assertions.assertThat(cost).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0_1.in", readFile("/day16/0_1.in"), 7036),
                Arguments.of("0.in", readFile("/day16/0.in"), 11048),
                Arguments.of("1.in", readFile("/day16/1.in"), 122492)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0_1.in", readFile("/day16/0_1.in"), 45),
                Arguments.of("0.in", readFile("/day16/0.in"), 64),
                Arguments.of("1.in", readFile("/day16/1.in"), 520)
        );
    }
}