package com.adventofcode.day15;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class LanternfishWarehouse {
    public long getBoxesCoordinatesSum(StringMatrix map, List<Direction> directions) {
        for (Direction direction : directions) {
            Pair<Integer> robotPosition = map.streamIndices().filter(p -> "@".equals(map.at(p))).findFirst().orElseThrow();
            for (Pair<Integer> toMove : getPositionsToMove(map, robotPosition, direction).reversed()) {
                Pair<Integer> moved = direction.moveFrom(toMove);
                map.set(moved.a(), moved.b(), map.at(toMove));
                map.set(toMove.a(), toMove.b(), ".");
            }
        }
        return map.streamIndices().filter(p -> "O".equals(map.at(p))).mapToLong(p -> p.a() + 100L * p.b()).sum();
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
            default -> throw new IllegalArgumentException("Unexpected argument: [" + neighbour + "]");
        };
    }

}
