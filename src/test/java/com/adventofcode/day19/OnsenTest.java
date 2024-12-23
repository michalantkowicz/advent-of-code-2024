package com.adventofcode.day19;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;

class OnsenTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperCountOfPossibleTowelDesigns(String description, List<String> towels, List<String> designs, long expected) {
        // given
        Onsen onsen = new Onsen();

        // when
        long count = onsen.countPossibleTowelDesigns(towels, designs);

        // then
        Assertions.assertThat(count).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperCountOfAllPossibleArrangements(String description, List<String> towels, List<String> designs, long expected) {
        // given
        Onsen onsen = new Onsen();

        // when
        long count = onsen.countAllPossibleArrangements(towels, designs);

        // then
        Assertions.assertThat(count).isEqualTo(expected);
    }

    private static List<String> parseTowels(String input) {
        String firstLine = input.lines().limit(1).findFirst().orElseThrow();
        return Arrays.stream(firstLine.split(",")).map(String::trim).toList();
    }

    private static List<String> parseDesigns(String input) {
        return input.lines().skip(2).toList();
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", parseTowels(readFile("/day19/0.in")), parseDesigns(readFile("/day19/0.in")), 6),
                Arguments.of("1.in", parseTowels(readFile("/day19/1.in")), parseDesigns(readFile("/day19/1.in")), 238)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", parseTowels(readFile("/day19/0.in")), parseDesigns(readFile("/day19/0.in")), 16),
                Arguments.of("1.in", parseTowels(readFile("/day19/1.in")), parseDesigns(readFile("/day19/1.in")), 635018909726691L)
        );
    }
}