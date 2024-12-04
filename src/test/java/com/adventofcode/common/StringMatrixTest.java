package com.adventofcode.common;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringMatrixTest {
    @ParameterizedTest
    @MethodSource("provideCharAtPosition")
    public void shouldReturnProperChar(int x, int y, String expected) {
        // given
        String input = """
                abcd
                efgh
                ijkl
                mnop
                """;
        Matrix<String> matrix = new StringMatrix(input, "\n");

        // when
        String result = matrix.at(x, y);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideCharAtPosition")
    public void shouldAlwaysReturnNullIfMatrixIsEmpty(int x, int y) {
        // given
        String input = "";
        Matrix<String> matrix = new StringMatrix(input, "\n");

        // when
        String result = matrix.at(x, y);

        // then
        assertThat(result).isNull();
    }

    private static Stream<Arguments> provideCharAtPosition() {
        return Stream.of(
                Arguments.of(0, 0, "a"),
                Arguments.of(3, 0, "d"),
                Arguments.of(0, 3, "m"),
                Arguments.of(3, 3, "p"),
                Arguments.of(1, 1, "f"),
                Arguments.of(1, 2, "j"),
                Arguments.of(-1, -1, null),
                Arguments.of(4, -1, null),
                Arguments.of(-1, 4, null),
                Arguments.of(4, 4, null)
        );
    }

    @Test
    public void shouldNotCreateImproperMatrix() {
        // given
        String input = """
                abcd
                efg
                ijkl
                mnop
                """;

        // then
        assertThatThrownBy(() -> new StringMatrix(input, "\n")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Line number 2 has length of 3");
    }
}