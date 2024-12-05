package com.adventofcode.day5;

import com.adventofcode.common.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getIntRows;
import static org.assertj.core.api.Assertions.assertThat;

class ManualPrinterTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void sumOfValidMiddleNumbersIsValid(String description, List<List<Integer>> rules, List<List<Integer>> updates, long expected) {
        // given
        ManualPrinter manualPrinter = new ManualPrinter();
        List<Pair<Integer>> rulesAsPairs = rules.stream().map(l -> new Pair<>(l.getFirst(), l.get(1))).toList();

        // when
        long sum = manualPrinter.sumValidMiddleNumbers(rulesAsPairs, updates);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", getIntRows("/day5/0_rules.in", "\\|"), getIntRows("/day5/0_pages.in", ","), 143),
                Arguments.of("1.in", getIntRows("/day5/1_rules.in", "\\|"), getIntRows("/day5/1_pages.in", ","), 4281)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void sumOfReorderedInvalidMiddleNumbersIsValid(String description, List<List<Integer>> rules, List<List<Integer>> updates, long expected) {
        // given
        ManualPrinter manualPrinter = new ManualPrinter();
        List<Pair<Integer>> rulesAsPairs = rules.stream().map(l -> new Pair<>(l.getFirst(), l.get(1))).toList();

        // when
        long sum = manualPrinter.sumInvalidReorderedMiddleNumbers(rulesAsPairs, updates);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", getIntRows("/day5/0_rules.in", "\\|"), getIntRows("/day5/0_pages.in", ","), 123),
                Arguments.of("1.in", getIntRows("/day5/1_rules.in", "\\|"), getIntRows("/day5/1_pages.in", ","), 5466)
        );
    }
}