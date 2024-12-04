package com.adventofcode.common;

public interface Matrix<T> {
    int width();

    int height();

    /**
     * @return value at [x, y] or null if index is out of bounds
     */
    T at(int x, int y);

    T set(int x, int y, T value);
}
