package com.adventofcode.common;

public enum Direction {
    TOP(new Pair<>(0, -1)),
    RIGHT(new Pair<>(1, 0)),
    DOWN(new Pair<>(0, 1)),
    LEFT(new Pair<>(-1, 0));

    private final Pair<Integer> vector;

    Direction(Pair<Integer> vector) {
        this.vector = vector;
    }

    public Pair<Integer> getVector() {
        return vector;
    }

    public Direction turnRight() {
        return switch (this) {
            case TOP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> TOP;
        };
    }

    public Pair<Integer> moveFrom(Pair<Integer> position) {
        return new Pair<>(position.a() + this.getVector().a(), position.b() + this.getVector().b());
    }
}
