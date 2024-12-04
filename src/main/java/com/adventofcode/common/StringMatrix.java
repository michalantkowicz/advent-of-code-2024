package com.adventofcode.common;

import java.util.ArrayList;
import java.util.List;

public class StringMatrix implements Matrix<String> {
    private final List<String> lines = new ArrayList<>();

    public StringMatrix(String string) {
        string.lines().forEach(line -> {
            lines.add(line);
            if (line.length() != lines.getFirst().length()) {
                throw new IllegalArgumentException("Provided string does not represent Matrix! " +
                        "Line number " + lines.size() + " has length of " + line.length() +
                        " while the first line has length " + lines.getFirst().length());
            }
        });
    }

    @Override
    public int width() {
        return lines.isEmpty() ? 0 : lines.getFirst().length();
    }

    @Override
    public int height() {
        return lines.isEmpty() ? 0 : lines.size();
    }

    @Override
    public String at(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            return null;
        }
        return String.valueOf(lines.get(y).charAt(x));
    }
}
