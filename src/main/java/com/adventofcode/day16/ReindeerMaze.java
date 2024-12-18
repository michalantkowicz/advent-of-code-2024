package com.adventofcode.day16;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ReindeerMaze {
    private record CostAndDirection(int cost, Direction direction) {
    }

    private static final Map<Pair<Integer>, Long> distance = new HashMap<>();
    private static final Map<Pair<Integer>, Direction> directions = new HashMap<>();
    private static final Map<Pair<Integer>, Pair<Integer>> previous = new HashMap<>();

    long getBestPathCost(StringMatrix map) {
        Pair<Integer> start = map.streamIndices().filter(p -> "S".equals(map.at(p))).findFirst().orElseThrow();
        Pair<Integer> end = map.streamIndices().filter(p -> "E".equals(map.at(p))).findFirst().orElseThrow();
        minCost(map, start);
        return distance.get(end);
    }

    long getBestPathTilesCount(StringMatrix map) {
        Pair<Integer> start = map.streamIndices().filter(p -> "S".equals(map.at(p))).findFirst().orElseThrow();
        Pair<Integer> end = map.streamIndices().filter(p -> "E".equals(map.at(p))).findFirst().orElseThrow();
        minCost(map, start);
        Set<Pair<Integer>> bestPathsTiles = new HashSet<>();
        Set<Pair<Integer>> toBeChecked = new HashSet<>(List.of(new Pair<>(end.a(), end.b())));

        while (!toBeChecked.isEmpty()) {
            Pair<Integer> c = toBeChecked.iterator().next();
            toBeChecked.remove(c);
            long lastCost = distance.get(c);

            while (previous.containsKey(c)) {
                long finalLastCost = lastCost;
                Pair<Integer> finalC = c;

                bestPathsTiles.add(c);

                // Get back to the all nodes that could be alternative route
                toBeChecked.addAll(
                        Arrays.stream(Direction.values())
                                .map(d -> d.moveFrom(finalC))
                                .filter(p -> !"#".equals(map.at(p)))
                                .filter(p -> !bestPathsTiles.contains(p))
                                .filter(p -> !p.equals(previous.get(finalC)))
                                .filter(p -> finalLastCost - distance.get(p) == 2)
                                .collect(Collectors.toSet())
                );

                lastCost = distance.get(c);
                c = previous.get(c);
            }
        }

        // First one is ignored
        return bestPathsTiles.size() + 1;
    }

    private void minCost(StringMatrix map, Pair<Integer> start) {
        Set<Pair<Integer>> Q = new HashSet<>(List.of(start));
        map.streamIndices().filter(p -> !"#".equals(map.at(p))).forEach(p -> {
            distance.put(p, Long.MAX_VALUE);
            Q.add(p);
        });
        distance.put(start, 0L);
        directions.put(start, Direction.RIGHT);

        while (!Q.isEmpty()) {
            Pair<Integer> p = getMin(Q);
            Q.remove(p);

            Arrays.stream(Direction.values())
                    .map(d -> d.moveFrom(p))
                    .filter(x -> !"#".equals(map.at(x)))
                    .toList()
                    .forEach(n -> {
                        CostAndDirection turnCost = getTurnCost(p, n);
                        if (distance.get(n) > distance.get(p) + 1 + turnCost.cost) {
                            distance.put(n, distance.get(p) + 1 + turnCost.cost);
                            directions.put(n, turnCost.direction);
                            previous.put(n, p);
                        }
                    });
        }
    }

    private CostAndDirection getTurnCost(Pair<Integer> previous, Pair<Integer> next) {
        return getTurnCost(previous, next, directions.get(previous));
    }

    private CostAndDirection getTurnCost(Pair<Integer> previous, Pair<Integer> next, Direction direction) {
        int leftCost = IntStream.rangeClosed(0, 3).filter(t -> next.equals(direction.turnLeft(t).moveFrom(previous))).findFirst().orElseThrow();
        int rightCost = IntStream.rangeClosed(0, 3).filter(t -> next.equals(direction.turnRight(t).moveFrom(previous))).findFirst().orElseThrow();

        if (leftCost < rightCost) {
            return new CostAndDirection(leftCost * 1000, direction.turnLeft(leftCost));
        } else {
            return new CostAndDirection(rightCost * 1000, direction.turnRight(rightCost));
        }
    }

    private Pair<Integer> getMin(Set<Pair<Integer>> p) {
        return p.stream().min(Comparator.comparing(distance::get)).orElseThrow();
    }
}