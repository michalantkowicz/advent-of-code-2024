package com.adventofcode.day11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class PlutoPebblesTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperCountAfterNBlinks(String description, String input, long expected) {
        // given
        PlutoPebbles pebbles = new PlutoPebbles();
        List<String> stones = Arrays.stream(input.split(" ")).toList();

        // when
        long count = pebbles.stonesCountAfterBlinks(stones, 25);

        // then
        assertThat(count).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperCountAfterHugeNBlinks(String description, String input, long expected) {
        // given
        PlutoPebbles pebbles = new PlutoPebbles();
        List<String> stones = Arrays.stream(input.split(" ")).toList();

        // when
        long count = pebbles.stonesCountAfterBlinks(stones, 75);

        // then
        assertThat(count).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day11/0.in"), 55312),
                Arguments.of("1.in", readFile("/day11/1.in"), 218079)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day11/0.in"), 65601038650482L),
                Arguments.of("1.in", readFile("/day11/1.in"), 259755538429618L)
        );
    }
}