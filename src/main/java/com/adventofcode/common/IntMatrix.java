package com.adventofcode.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntMatrix implements Matrix<Integer> {
    private final List<List<Integer>> lines = new ArrayList<>();

    public IntMatrix(String string) {
        string.lines().forEach(line -> {
            lines.add(Arrays.stream(line.split("")).map(Integer::parseInt).toList());
            if (line.length() != lines.getFirst().size()) {
                throw new IllegalArgumentException("Provided string does not represent Matrix! " +
                        "Line number " + lines.size() + " has length of " + line.length() +
                        " while the first line has length " + lines.getFirst().size());
            }
        });
    }

    public IntMatrix(int width, int height, int fill) {
        for (int i = 0; i < height; i++) {
            List<Integer> l = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                l.add(fill);
            }
            lines.add(l);
        }
    }

    @Override
    public int width() {
        return lines.isEmpty() ? 0 : lines.getFirst().size();
    }

    @Override
    public int height() {
        return lines.isEmpty() ? 0 : lines.size();
    }

    public Integer at(Pair<Integer> position) {
        return at(position.a(), position.b());
    }

    @Override
    public Integer at(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            return null;
        }
        return lines.get(y).get(x);
    }

    @Override
    public void set(int x, int y, Integer value) {
        if (value > 10) {
            throw new IllegalArgumentException("value must be [0-9]");
        }
        lines.get(y).set(x, value);
    }
}
