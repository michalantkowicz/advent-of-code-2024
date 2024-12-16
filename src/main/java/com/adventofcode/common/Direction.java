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

    public Direction turnRight(int times) {
        Direction result = this;
        for(int i = 0; i < times; i++) {
            result = result.turnRight();
        }
        return result;
    }

    public Direction turnLeft() {
        return switch (this) {
            case TOP -> LEFT;
            case LEFT -> DOWN;
            case DOWN -> RIGHT;
            case RIGHT -> TOP;
        };
    }

    public Direction turnLeft(int times) {
        Direction result = this;
        for(int i = 0; i < times; i++) {
            result = result.turnLeft();
        }
        return result;
    }

    public Direction turnAround() {
        return switch (this) {
            case TOP -> DOWN;
            case LEFT -> RIGHT;
            case DOWN -> TOP;
            case RIGHT -> LEFT;
        };
    }

    public Pair<Integer> moveFrom(Pair<Integer> position) {
        return new Pair<>(position.a() + this.getVector().a(), position.b() + this.getVector().b());
    }
}
