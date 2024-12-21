package com.adventofcode.day20;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.*;

class Racetrack {
    long countCheatsForExactly(StringMatrix map, long picoseconds) {
        return countCheatsCountBySaving(map).getOrDefault(picoseconds, Collections.emptyList()).size();
    }

    long countCheatsForAtLeast(StringMatrix map, long picoseconds) {
        Map<Long, List<Pair<Pair<Integer>>>> cheatsCountBySaving = countCheatsCountBySaving(map);
        return cheatsCountBySaving.keySet().stream()
                .filter(k -> k >= picoseconds)
                .mapToLong(k -> cheatsCountBySaving.get(k).size())
                .sum();
    }

    private Map<Long, List<Pair<Pair<Integer>>>> countCheatsCountBySaving(StringMatrix map) {
        Pair<Integer> start = map.streamIndices().filter(p -> "S".equals(map.at(p))).findFirst().orElseThrow();
        Pair<Integer> end = map.streamIndices().filter(p -> "E".equals(map.at(p))).findFirst().orElseThrow();

        Map<Pair<Integer>, Long> distanceToEnd = minCost(map, end);
        Map<Pair<Integer>, Long> distanceFromStart = minCost(map, start);

        long totalDistance = distanceFromStart.get(end);

        Map<Long, List<Pair<Pair<Integer>>>> cheatCountBySaving = new HashMap<>();

        map.streamIndices().filter(p -> !"#".equals(map.at(p))).forEach(p -> {
            for (Pair<Pair<Integer>> cheat : getPossibleCheats(map, p)) {
                long saving = totalDistance - (distanceFromStart.get(p) + distanceToEnd.get(cheat.b())) - 2;
                cheatCountBySaving.computeIfAbsent(saving, _ -> new ArrayList<>()).add(cheat);
            }
        });

        return cheatCountBySaving;
    }

    private List<Pair<Pair<Integer>>> getPossibleCheats(StringMatrix map, Pair<Integer> p) {
        List<Pair<Pair<Integer>>> result = new ArrayList<>();
        for (Direction d : Direction.values()) {
            Pair<Integer> next = d.moveFrom(p);
            Pair<Integer> target = d.moveFrom(next);
            if ("#".equals(map.at(next)) && (".".equals(map.at(target)) || "E".equals(map.at(target)))) {
                result.add(new Pair<>(next, target));
            }
        }
        return result;
    }

    private Map<Pair<Integer>, Long> minCost(StringMatrix map, Pair<Integer> start) {
        Map<Pair<Integer>, Long> distance = new HashMap<>();
        Set<Pair<Integer>> Q = new HashSet<>(List.of(start));
        map.streamIndices().filter(p -> !"#".equals(map.at(p))).forEach(p -> {
            distance.put(p, Long.MAX_VALUE);
            Q.add(p);
        });
        distance.put(start, 0L);

        while (!Q.isEmpty()) {
            Pair<Integer> p = Q.stream().min(Comparator.comparing(distance::get)).orElseThrow();
            Q.remove(p);
            if (distance.get(p) == Long.MAX_VALUE) {
                continue;
            }

            Arrays.stream(Direction.values())
                    .map(d -> d.moveFrom(p))
                    .filter(x -> !"#".equals(map.at(x)))
                    .toList()
                    .forEach(n -> {
                        if (distance.get(n) > distance.get(p) + 1) {
                            distance.put(n, distance.get(p) + 1);
                        }
                    });
        }
        return distance;
    }
}
