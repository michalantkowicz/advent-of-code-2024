package com.adventofcode.day18;

import com.adventofcode.common.IntMatrix;
import com.adventofcode.common.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getIntPairs;

class MemoryTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperCostOfShortestPath(String description, int limit, IntMatrix space, List<Pair<Integer>> bytes, long expected) {
        // given
        Memory memory = new Memory();

        // when
        long shortestPath = memory.calculateShortestPath(space, bytes, limit);

        // then
        Assertions.assertThat(shortestPath).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperByte(String description, int limit, IntMatrix space, List<Pair<Integer>> bytes, Pair<Integer> expected) {
        // given
        Memory memory = new Memory();

        // when
        Pair<Integer> b = memory.calculateFirstByte(space, bytes, limit);

        // then
        Assertions.assertThat(b).isNotEqualTo(bytes.get(limit));
        Assertions.assertThat(b).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", 12, new IntMatrix(7, 7, 0), getIntPairs("/day18/0.in", ","), 22),
                Arguments.of("1.in", 1024, new IntMatrix(71, 71, 0), getIntPairs("/day18/1.in", ","), 340)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", 0, new IntMatrix(7, 7, 0), getIntPairs("/day18/0.in", ","), new Pair<>(6, 1)),
                Arguments.of("1.in", 2910, new IntMatrix(71, 71, 0), getIntPairs("/day18/1.in", ","), new Pair<>(34, 32))
        );
    }
}