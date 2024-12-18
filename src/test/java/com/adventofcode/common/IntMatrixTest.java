package com.adventofcode.common;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class IntMatrixTest {
    @Test
    public void shouldCreateProperMatrix() {
        // given
        Matrix<Integer> matrix = new IntMatrix(10, 10, 0);

        // then
        Assertions.assertThat(matrix.at(0, 0)).isEqualTo(0);
        Assertions.assertThat(matrix.at(9, 0)).isEqualTo(0);
        Assertions.assertThat(matrix.at(0, 9)).isEqualTo(0);
        Assertions.assertThat(matrix.at(9, 9)).isEqualTo(0);
        Assertions.assertThat(matrix.at(10, 0)).isNull();
        Assertions.assertThat(matrix.at(0, 10)).isNull();
    }

    @Test
    public void shouldCreateProperEmptyMatrix() {
        // given
        Matrix<Integer> matrix = new IntMatrix(0, 0, 0);

        // then
        Assertions.assertThat(matrix.at(0, 0)).isNull();
        Assertions.assertThat(matrix.at(1, 1)).isNull();
    }
}