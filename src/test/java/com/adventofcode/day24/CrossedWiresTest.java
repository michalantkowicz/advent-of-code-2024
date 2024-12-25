package com.adventofcode.day24;

import com.adventofcode.common.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;

class CrossedWiresTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldCalculateProperNumber(String description, Map<String, Integer> values, List<Input> input, long expected) {
        // given
        CrossedWires wires = new CrossedWires();

        // when
        long number = wires.calculateNumber(values, input, false);

        // then
        Assertions.assertThat(number).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void printoutNodes(String description, Map<String, Integer> values, List<Input> input) {
        // given
        CrossedWires wires = new CrossedWires();
        input = new ArrayList<>(input);
        input.sort(Comparator.comparing(i -> i.records().a()));

        // when
        wires.calculateNumber(values, input, true);

        // then
        // Take the output and put to https://dreampuf.github.io/GraphvizOnline/
        // Search for z nodes that do not have:
        //   * one green input arrow that is derived from x and y nodes of the same number (e.g. x11, y11 for z11)
        //   * one green input arrow that is derived from node that has two red arrows from previous cluster
        // Search for node that should be swapped with this z node and swap it
        // For given input pairs are: [z11, vkq], [z24, mmk], [z38, hqh], [pvb, qdq]

        Assertions.assertThat(true).isTrue();
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

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("1.in", parseValues(readFile("/day24/1.in")), parseInput(readFile("/day24/1.in")))
        );
    }
}