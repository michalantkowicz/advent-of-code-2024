package com.adventofcode.day6;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

class LabPatrol {
    long calculatePatrolDistance(StringMatrix stringMatrix, Pair<Integer> position) {
        long distance = 1;
        Direction direction = Direction.TOP;

        while (true) {
            stringMatrix.set(position.a(), position.b(), "X");
            Pair<Integer> newPosition = direction.moveFrom(position);
            if (stringMatrix.at(newPosition.a(), newPosition.b()) == null) {
                break;
            } else if ("#".equals(stringMatrix.at(newPosition.a(), newPosition.b()))) {
                direction = direction.turnRight();
            } else {
                position = newPosition;
                if (!"X".equals(stringMatrix.at(position.a(), position.b()))) {
                    distance++;
                }
            }
        }
        return distance;
    }

    long checkObstructions(StringMatrix stringMatrix, Pair<Integer> position) {
        calculatePatrolDistance(stringMatrix, position);
        return stringMatrix.streamIndices().filter(p -> !p.equals(position)).filter(p -> "X".equals(stringMatrix.at(p))).filter(p -> {
            stringMatrix.set(p.a(), p.b(), "#");
            boolean inLoop = isInLoop(stringMatrix, position);
            stringMatrix.set(p.a(), p.b(), "X");
            return inLoop;
        }).count();
    }

    private boolean isInLoop(StringMatrix matrix, Pair<Integer> position) {
        long distance = 0;
        Direction direction = Direction.TOP;
        while (true) {
            Pair<Integer> newPosition = direction.moveFrom(position);
            if (matrix.at(newPosition) == null) {
                return false;
            } else if ("#".equals(matrix.at(newPosition))) {
                direction = direction.turnRight();
            } else {
                if (distance > 10_000) {
                    return true;
                }
                position = newPosition;
                distance++;
            }
        }
    }
}
