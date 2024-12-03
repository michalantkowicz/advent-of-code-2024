package com.adventofcode.day3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class CorruptedProgramTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void sumProperMultiplications(String description, String input, long expected) {
        // given
        CorruptedProgram corruptedProgram = new CorruptedProgram();

        // when
        long sum = corruptedProgram.sumProperMultiplications(input);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void sumMultiplicationsIgnoringDonts(String description, String input, long expected) {
        // given
        CorruptedProgram corruptedProgram = new CorruptedProgram();

        // when
        long sum = corruptedProgram.sumMultiplicationsIgnoringDonts(input);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day3/0.in"), 161),
                Arguments.of("1.in", readFile("/day3/1.in"), 173731097)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0_2.in", readFile("/day3/0_2.in"), 48),
                Arguments.of("1.in", readFile("/day3/1.in"), 93729253)
        );
    }
}