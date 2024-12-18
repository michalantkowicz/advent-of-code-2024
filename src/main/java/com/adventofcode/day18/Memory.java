package com.adventofcode.day18;

import com.adventofcode.common.Direction;
import com.adventofcode.common.IntMatrix;
import com.adventofcode.common.Pair;

import java.util.*;

class Memory {
    private static final Map<Pair<Integer>, Long> distance = new HashMap<>();

    long calculateShortestPath(IntMatrix space, List<Pair<Integer>> bytes, int limit) {
        Pair<Integer> start = new Pair<>(0, 0);
        Pair<Integer> end = new Pair<>(space.width() - 1, space.height() - 1);

        bytes.stream().limit(limit).forEach(b -> space.set(b.a(), b.b(), -1));

        minCost(space, start);

        return distance.get(end);
    }

    Pair<Integer> calculateFirstByte(IntMatrix space, List<Pair<Integer>> bytes, int limit) {
        Pair<Integer> start = new Pair<>(0, 0);
        Pair<Integer> end = new Pair<>(space.width() - 1, space.height() - 1);

        bytes.stream().limit(limit).forEach(b -> space.set(b.a(), b.b(), -1));
        Pair<Integer> currentByte;

        do {
            currentByte = bytes.get(limit++);
            space.set(currentByte.a(), currentByte.b(), -1);
            distance.clear();
            minCost(space, start);
        } while (distance.get(end) < Long.MAX_VALUE);

        return currentByte;
    }


    private void minCost(IntMatrix map, Pair<Integer> start) {
        Set<Pair<Integer>> Q = new HashSet<>(List.of(start));
        map.streamIndices().filter(p -> map.at(p) >= 0).forEach(p -> {
            distance.put(p, Long.MAX_VALUE);
            Q.add(p);
        });
        distance.put(start, 0L);

        while (!Q.isEmpty()) {
            Pair<Integer> p = getMin(Q);
            Q.remove(p);
            if (distance.get(p) == Long.MAX_VALUE) {
                continue;
            }

            Arrays.stream(Direction.values())
                    .map(d -> d.moveFrom(p))
                    .filter(x -> map.at(x) != null)
                    .filter(x -> map.at(x) >= 0)
                    .toList()
                    .forEach(n -> {
                        if (distance.get(n) > distance.get(p) + 1) {
                            distance.put(n, distance.get(p) + 1);
                        }
                    });
        }
    }

    private Pair<Integer> getMin(Set<Pair<Integer>> p) {
        return p.stream().min(Comparator.comparing(distance::get)).orElseThrow();
    }
}
