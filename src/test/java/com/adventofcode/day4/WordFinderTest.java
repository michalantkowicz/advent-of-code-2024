package com.adventofcode.day4;

import com.adventofcode.common.StringMatrix;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class WordFinderTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void findsAllXmas(String description, String input, long expected) {
        // given
        WordFinder wordFinder = new WordFinder();

        // when
        long count = wordFinder.countXmas(new StringMatrix(input));

        // then
        assertThat(count).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void findsAllXmasCrossed(String description, String input, long expected) {
        // given
        WordFinder wordFinder = new WordFinder();

        // when
        long count = wordFinder.countXmasCrossed(new StringMatrix(input));

        // then
        assertThat(count).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day4/0.in"), 18),
                Arguments.of("1.in", readFile("/day4/1.in"), 2573)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day4/0.in"), 9),
                Arguments.of("1.in", readFile("/day4/1.in"), 1850)
        );
    }
}