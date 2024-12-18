package com.adventofcode.day18;

import com.adventofcode.common.Direction;
import com.adventofcode.common.IntMatrix;
import com.adventofcode.common.Pair;

import java.util.*;

class Memory {
    private static final Map<Pair<Integer>, Long> distance = new HashMap<>();
    private static final Map<Pair<Integer>, Pair<Integer>> previous = new HashMap<>();

    public long calculateShortestPath(IntMatrix space, List<Pair<Integer>> bytes, int limit) {
        Pair<Integer> start = new Pair<>(0, 0);
        Pair<Integer> end = new Pair<>(space.width() - 1, space.height() - 1);

        bytes.stream().limit(limit).forEach(b -> space.set(b.a(), b.b(), -1));

        minCost(space, start);

        return distance.get(end);
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

            Arrays.stream(Direction.values())
                    .map(d -> d.moveFrom(p))
                    .filter(x -> map.at(x) != null)
                    .filter(x -> map.at(x) >= 0)
                    .toList()
                    .forEach(n -> {
                        if (distance.get(n) > distance.get(p) + 1) {
                            distance.put(n, distance.get(p) + 1);
                            previous.put(n, p);
                        }
                    });
        }
    }

    private Pair<Integer> getMin(Set<Pair<Integer>> p) {
        return p.stream().min(Comparator.comparing(distance::get)).orElseThrow();
    }
}
