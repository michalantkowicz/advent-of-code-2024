package com.adventofcode.day20;

import com.adventofcode.common.StringMatrix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;

class RacetrackTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideValidationInput")
    void shouldReturnProperCountOfCheatsForExactValue(String description, StringMatrix map, int picoseconds, long expected) {
        // given
        Racetrack racetrack = new Racetrack();

        // when
        long count = racetrack.countCheatsForExactly(map, picoseconds);

        // then
        Assertions.assertThat(count).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperCountOfCheatsForAtLeastValue(String description, StringMatrix map, int picoseconds, long expected) {
        // given
        Racetrack racetrack = new Racetrack();

        // when
        long count = racetrack.countCheatsForAtLeast(map, picoseconds);

        // then
        Assertions.assertThat(count).isEqualTo(expected);
    }

    private static Stream<Arguments> provideValidationInput() {
        return Stream.of(
                Arguments.of("0_a.in", readFile("/day20/0.in"), 2, 14),
                Arguments.of("0_b.in", readFile("/day20/0.in"), 4, 14),
                Arguments.of("0_c.in", readFile("/day20/0.in"), 6, 2),
                Arguments.of("0_d.in", readFile("/day20/0.in"), 8, 4),
                Arguments.of("0_e.in", readFile("/day20/0.in"), 10, 2),
                Arguments.of("0_f.in", readFile("/day20/0.in"), 12, 3),
                Arguments.of("0_g.in", readFile("/day20/0.in"), 20, 1),
                Arguments.of("0_h.in", readFile("/day20/0.in"), 36, 1),
                Arguments.of("0_i.in", readFile("/day20/0.in"), 38, 1),
                Arguments.of("0_j.in", readFile("/day20/0.in"), 40, 1),
                Arguments.of("0_k.in", readFile("/day20/0.in"), 64, 1)
        );
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("1.in", readFile("/day20/1.in"), 100, 1426)
        );
    }
}