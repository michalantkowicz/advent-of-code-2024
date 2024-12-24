package com.adventofcode.day15;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LanternfishWarehouse {
    long getBoxesCoordinatesSum(StringMatrix map, List<Direction> directions) {
        for (Direction direction : directions) {
            Pair<Integer> robotPosition = map.streamIndices().filter(p -> "@".equals(map.at(p))).findFirst().orElseThrow();
            List<Pair<Integer>> reversed = getPositionsToMove(map, robotPosition, direction).reversed();
            for (Pair<Integer> toMove : reversed) {
                Pair<Integer> moved = direction.moveFrom(toMove);
                map.set(moved.a(), moved.b(), map.at(toMove));
                map.set(toMove.a(), toMove.b(), ".");
            }
        }
        return map.streamIndices().filter(p -> "O".equals(map.at(p))).mapToLong(p -> p.a() + 100L * p.b()).sum() +
                map.streamIndices().filter(p -> "[".equals(map.at(p))).mapToLong(p -> p.a() + 100L * p.b()).sum();
    }

    private List<Pair<Integer>> getPositionsToMove(StringMatrix map, Pair<Integer> position, Direction direction) {
        String neighbour = map.at(direction.moveFrom(position));
        return switch (neighbour) {
            case "." -> List.of(position);
            case "#" -> Collections.emptyList();
            case "O" -> {
                List<Pair<Integer>> nextMoves = getPositionsToMove(map, direction.moveFrom(position), direction);
                yield (nextMoves.isEmpty()) ? Collections.emptyList() : Stream.of(List.of(position), nextMoves).flatMap(List::stream).toList();
            }
            case "[", "]" -> {
                if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                    List<Pair<Integer>> nextMoves = getPositionsToMove(map, direction.moveFrom(position), direction);
                    yield (nextMoves.isEmpty()) ? Collections.emptyList() : Stream.of(List.of(position), nextMoves).flatMap(List::stream).toList();
                } else {
                    List<Pair<Integer>> nextMoves = getPositionsToMove(map, direction.moveFrom(position), direction);
                    List<Pair<Integer>> nextSideMoves = new ArrayList<>();
                    if (neighbour.equals("[")) {
                        nextSideMoves.addAll(getPositionsToMove(map, direction.moveFrom(sum(new Pair<>(1, 0), position)), direction));
                    } else {
                        nextSideMoves.addAll(getPositionsToMove(map, direction.moveFrom(sum(new Pair<>(-1, 0), position)), direction));
                    }
                    if (nextMoves.isEmpty() || nextSideMoves.isEmpty()) {
                        yield Collections.emptyList();
                    } else {
                        List<Pair<Integer>> result = Stream.of(List.of(position), nextMoves, nextSideMoves).flatMap(List::stream).collect(Collectors.toSet()).stream().toList();
                        if(direction == Direction.TOP) {
                            yield result.stream().sorted(Comparator.comparing(Pair<Integer>::b, Comparator.reverseOrder()).thenComparing(Pair::a, Comparator.reverseOrder())).toList();
                        } else {
                            yield result.stream().sorted(Comparator.comparing(Pair<Integer>::b).thenComparing(Pair::a, Comparator.reverseOrder())).toList();
                        }
                    }
                }
            }
            default -> throw new IllegalArgumentException("Unexpected argument: [" + neighbour + "]");
        };
    }

    private static Pair<Integer> sum(Pair<Integer> a, Pair<Integer> b) {
        return new Pair<>(a.a() + b.a(), a.b() + b.b());
    }
}