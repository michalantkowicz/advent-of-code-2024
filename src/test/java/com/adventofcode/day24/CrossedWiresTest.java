package com.adventofcode.day24;

import com.adventofcode.common.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;

class CrossedWiresTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldCalculateProperNumber(String description, Map<String, Integer> values, List<Input> input, long expected) {
        // given
        CrossedWires wires = new CrossedWires();

        // when
        long number = wires.calculateNumber(values, input);

        // then
        Assertions.assertThat(number).isEqualTo(expected);
    }

    private static Map<String, Integer> parseValues(String file) {
        String valuesPart = file.split("(?m)^\\s*$")[0];
        Map<String, Integer> result = new HashMap<>();
        valuesPart.lines().forEach(l -> result.put(l.split(": ")[0], Integer.parseInt(l.split(": ")[1])));
        return result;
    }

    private static List<Input> parseInput(String file) {
        String valuesPart = file.split("(?m)^\\s*$")[1];
        return valuesPart.lines().skip(1).map(l -> l.split(" ")).map(s -> new Input(new Pair<>(s[0], s[2]), Operation.of(s[1]), s[4])).toList();
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0_1.in", parseValues(readFile("/day24/0_1.in")), parseInput(readFile("/day24/0_1.in")), 4),
                Arguments.of("0.in", parseValues(readFile("/day24/0.in")), parseInput(readFile("/day24/0.in")), 2024),
                Arguments.of("1.in", parseValues(readFile("/day24/1.in")), parseInput(readFile("/day24/1.in")), 48063513640678L)
        );
    }
}