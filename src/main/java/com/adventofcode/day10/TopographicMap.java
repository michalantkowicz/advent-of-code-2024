package com.adventofcode.day10;

import com.adventofcode.common.Direction;
import com.adventofcode.common.IntMatrix;
import com.adventofcode.common.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TopographicMap {
    long sumTrailheadsScores(IntMatrix matrix) {
        return matrix.streamIndices()
                .filter(p -> matrix.at(p).equals(0))
                .map(p -> getAllReachableNines(matrix, p, new HashSet<>(), new HashSet<>()))
                .mapToLong(Set::size)
                .sum();
    }

    private Set<Pair<Integer>> getAllReachableNines(IntMatrix matrix, Pair<Integer> position, Set<Pair<Integer>> visited, Set<Pair<Integer>> nines) {
        visited.add(position);

        List<Pair<Integer>> neighbours = Arrays.stream(Direction.values())
                .map(d -> d.moveFrom(position))
                .filter(p -> matrix.at(p) != null)
                .filter(p -> !visited.contains(p))
                .filter(p -> matrix.at(p) == matrix.at(position) + 1)
                .toList();

        if (!neighbours.isEmpty()) {
            neighbours.stream().filter(n -> matrix.at(n) == 9).peek(visited::add).forEach(nines::add);
            neighbours.stream().filter(n -> matrix.at(n) != 9).forEach(n -> nines.addAll(getAllReachableNines(matrix, n, visited, nines)));
        }
        return nines;
    }
}
