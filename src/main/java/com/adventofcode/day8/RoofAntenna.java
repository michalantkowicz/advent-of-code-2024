package com.adventofcode.day8;

import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.*;
import java.util.stream.Collectors;

class RoofAntenna {
    public long countAntinodes(StringMatrix map) {
        Set<Pair<Integer>> antinodesPositions = new HashSet<>();
        Map<String, List<Pair<Integer>>> positionsPerType = map.streamIndices()
                .filter(p -> !".".equals(map.at(p)))
                .collect(Collectors.groupingBy(map::at));
        for (String type : positionsPerType.keySet()) {
            List<Pair<Integer>> antennasPositions = positionsPerType.get(type);
            for (Pair<Integer> a : antennasPositions) {
                for (Pair<Integer> b : antennasPositions) {
                    antinodesPositions.addAll(findAntinodes(map, a, b));
                }
            }
        }
        return antinodesPositions.size();
    }

    private List<Pair<Integer>> findAntinodes(StringMatrix map, Pair<Integer> a, Pair<Integer> b) {
        if (a.equals(b)) {
            return Collections.emptyList();
        } else {
            List<Pair<Integer>> result = new ArrayList<>();
            Pair<Integer> distance = new Pair<>(b.a() - a.a(), b.b() - a.b());
            Pair<Integer> antinodeAB = new Pair<>(b.a() + distance.a(), b.b() + distance.b());
            Pair<Integer> antinodeBA = new Pair<>(a.a() - distance.a(), a.b() - distance.b());
            if (map.at(antinodeAB) != null) {
                result.add(antinodeAB);
            }
            if (map.at(antinodeBA) != null) {
                result.add(antinodeBA);
            }
            return result;
        }
    }
}
