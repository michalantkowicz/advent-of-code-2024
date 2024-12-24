package com.adventofcode.day24;

import com.adventofcode.common.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getLines;

class CrossedWiresTest {
    @Disabled("[day24 part 1] Not implemented")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldCalculateProperNumber(String description, List<Pair<String>> values, List<String> input, long expected) {
        // given
        CrossedWires wires = new CrossedWires();

        // when
        long number = wires.calculateNumber(values, input);

        // then
        Assertions.assertThat(number).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0_1.in", getLines("/day24/0_1.in"), 4),
                Arguments.of("0.in", getLines("/day24/0.in"), 2024),
                Arguments.of("1.in", getLines("/day24/1.in"), -1)
        );
    }
}