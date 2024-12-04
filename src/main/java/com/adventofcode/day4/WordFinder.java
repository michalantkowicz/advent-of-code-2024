package com.adventofcode.day4;

import com.adventofcode.common.Matrix;
import com.adventofcode.common.Pair;

import java.util.List;

import static java.util.stream.Collectors.joining;

class WordFinder {
    long countXmas(Matrix<String> matrix) {
        List<List<Pair<Integer>>> paths = List.of(
                List.of(new Pair<>(1, 0), new Pair<>(2, 0), new Pair<>(3, 0)),
                List.of(new Pair<>(-1, 0), new Pair<>(-2, 0), new Pair<>(-3, 0)),
                List.of(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)),
                List.of(new Pair<>(0, -1), new Pair<>(0, -2), new Pair<>(0, -3)),
                List.of(new Pair<>(1, 1), new Pair<>(2, 2), new Pair<>(3, 3)),
                List.of(new Pair<>(-1, -1), new Pair<>(-2, -2), new Pair<>(-3, -3)),
                List.of(new Pair<>(1, -1), new Pair<>(2, -2), new Pair<>(3, -3)),
                List.of(new Pair<>(-1, 1), new Pair<>(-2, 2), new Pair<>(-3, 3))
        );

        return matrix.streamIndices()
                .filter(i -> "X".equals(matrix.at(i.a(), i.b())))
                .mapToLong(i ->
                        paths.stream().filter(path -> "MAS".equals(path.stream().map(p -> matrix.at(i.a() + p.a(), i.b() + p.b())).collect(joining()))).count()
                )
                .sum();
    }

    long countXmasCrossed(Matrix<String> matrix) {
        List<List<Pair<Integer>>> paths = List.of(
                List.of(new Pair<>(-1, -1), new Pair<>(1, 1), new Pair<>(1, -1), new Pair<>(-1, 1)),
                List.of(new Pair<>(-1, -1), new Pair<>(1, 1), new Pair<>(-1, 1), new Pair<>(1, -1)),
                List.of(new Pair<>(1, 1), new Pair<>(-1, -1), new Pair<>(1, -1), new Pair<>(-1, 1)),
                List.of(new Pair<>(1, 1), new Pair<>(-1, -1), new Pair<>(-1, 1), new Pair<>(1, -1)),
                List.of(new Pair<>(1, -1), new Pair<>(-1, 1), new Pair<>(-1, -1), new Pair<>(1, 1)),
                List.of(new Pair<>(1, -1), new Pair<>(-1, 1), new Pair<>(1, 1), new Pair<>(-1, -1)),
                List.of(new Pair<>(-1, 1), new Pair<>(1, -1), new Pair<>(-1, -1), new Pair<>(1, 1)),
                List.of(new Pair<>(-1, 1), new Pair<>(1, -1), new Pair<>(1, 1), new Pair<>(-1, -1))
        );

        return matrix.streamIndices()
                .filter(i -> "A".equals(matrix.at(i.a(), i.b())))
                .mapToLong(i ->
                        paths.stream().filter(path -> "MSMS".equals(path.stream().map(p -> matrix.at(i.a() + p.a(), i.b() + p.b())).collect(joining()))).count() / 2
                )
                .sum();
    }
}
