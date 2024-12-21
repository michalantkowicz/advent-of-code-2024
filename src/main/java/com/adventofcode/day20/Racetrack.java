package com.adventofcode.day20;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.*;
import java.util.function.BiFunction;

class Racetrack {
    long countCheatsForExactly(StringMatrix map, long picoseconds) {
        return countCheatsCountBySaving(map, this::getPossibleCheats).getOrDefault(picoseconds, Collections.emptyList()).size();
    }

    long countCheatsForAtLeast(StringMatrix map, long picoseconds) {
        Map<Long, List<Cheat>> cheatsCountBySaving = countCheatsCountBySaving(map, this::getPossibleCheats);
        return cheatsCountBySaving.keySet().stream()
                .filter(k -> k >= picoseconds)
                .mapToLong(k -> cheatsCountBySaving.get(k).size())
                .sum();
    }

    long countCheatsForAtLeastWithLongerCheats(StringMatrix map, int picoseconds) {
        Map<Long, List<Cheat>> cheatsCountBySaving = countCheatsCountBySaving(map, this::getPossibleLongerCheats);
        return cheatsCountBySaving.keySet().stream()
                .filter(k -> k >= picoseconds)
                .mapToLong(k -> cheatsCountBySaving.get(k).size())
                .sum();
    }

    private Map<Long, List<Cheat>> countCheatsCountBySaving(StringMatrix map, BiFunction<StringMatrix, Pair<Integer>, List<Cheat>> cheatsProvider) {
        Pair<Integer> start = map.streamIndices().filter(p -> "S".equals(map.at(p))).findFirst().orElseThrow();
        Pair<Integer> end = map.streamIndices().filter(p -> "E".equals(map.at(p))).findFirst().orElseThrow();

        Map<Pair<Integer>, Long> distanceToEnd = minCost(map, end);
        Map<Pair<Integer>, Long> distanceFromStart = minCost(map, start);

        long totalDistance = distanceFromStart.get(end);

        Map<Long, List<Cheat>> cheatCountBySaving = new HashMap<>();

        map.streamIndices().filter(p -> !"#".equals(map.at(p))).forEach(p -> {
            for (Cheat cheat : cheatsProvider.apply(map, p)) {
                long saving = totalDistance - (distanceFromStart.get(p) + distanceToEnd.get(cheat.target())) - cheat.cost();
                cheatCountBySaving.computeIfAbsent(saving, _ -> new ArrayList<>()).add(cheat);
            }
        });

        return cheatCountBySaving;
    }

    private List<Cheat> getPossibleCheats(StringMatrix map, Pair<Integer> p) {
        List<Cheat> result = new ArrayList<>();
        for (Direction d : Direction.values()) {
            Pair<Integer> next = d.moveFrom(p);
            Pair<Integer> target = d.moveFrom(next);
            if ("#".equals(map.at(next)) && (".".equals(map.at(target)) || "E".equals(map.at(target)))) {
                result.add(new Cheat(target, 2));
            }
        }
        return result;
    }

    private List<Cheat> getPossibleLongerCheats(StringMatrix map, Pair<Integer> p) {
        List<Cheat> result = new ArrayList<>();
        for (int i = p.a() - 20; i <= p.a() + 20; i++) {
            for (int j = p.b() - 20; j <= p.b() + 20; j++) {
                if (".".equals(map.at(i, j)) || "E".equals(map.at(i, j))) {
                    int distance = Math.abs(i - p.a()) + Math.abs(j - p.b());
                    if (distance <= 20) {
                        result.add(new Cheat(new Pair<>(i, j), distance));
                    }
                }
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
