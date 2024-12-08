package com.adventofcode.day8;

import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RoofAntenna {
    long countAntinodes(StringMatrix map) {
        Map<String, List<Pair<Integer>>> positionsPerType = getPositionsPerType(map);
        return positionsPerType.keySet().stream()
                .map(type -> findAntinodes(positionsPerType, type, map, 1))
                .flatMap(Set::stream)
                .collect(Collectors.toSet())
                .size();
    }

    long countAntinodesWithUpdatedModel(StringMatrix map) {
        Map<String, List<Pair<Integer>>> positionsPerType = getPositionsPerType(map);
        Set<Pair<Integer>> antinodesPositions = positionsPerType.keySet().stream()
                .map(type -> findAntinodes(positionsPerType, type, map, Math.max(map.width(), map.height())))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        antinodesPositions.addAll(positionsPerType.values().stream().flatMap(List::stream).toList());
        return antinodesPositions.size();
    }

    private static Map<String, List<Pair<Integer>>> getPositionsPerType(StringMatrix map) {
        return map.streamIndices()
                .filter(p -> !".".equals(map.at(p)))
                .collect(Collectors.groupingBy(map::at));
    }

    private static Set<Pair<Integer>> findAntinodes(Map<String, List<Pair<Integer>>> positionsPerType, String type, StringMatrix map, int maxIterations) {
        Set<Pair<Integer>> antinodesPositions = new HashSet<>();
        List<Pair<Integer>> antennasPositions = positionsPerType.get(type);
        for (Pair<Integer> a : antennasPositions) {
            for (Pair<Integer> b : antennasPositions) {
                antinodesPositions.addAll(findAntinodesInLine(map, a, b, maxIterations));
            }
        }
        return antinodesPositions;
    }

    private static List<Pair<Integer>> findAntinodesInLine(StringMatrix map, Pair<Integer> a, Pair<Integer> b, int maxIterations) {
        if (a.equals(b)) {
            return Collections.emptyList();
        }
        Pair<Integer> distance = new Pair<>(b.a() - a.a(), b.b() - a.b());
        return IntStream.range(1, maxIterations + 1)
                .mapToObj(i -> new Pair<>(b.a() + i * distance.a(), b.b() + i * distance.b()))
                .filter(p -> map.at(p) != null)
                .toList();
    }
}
