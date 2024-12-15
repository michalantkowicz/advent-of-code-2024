package com.adventofcode.day14;

import com.adventofcode.common.Pair;

public record Quadrant(int minx, int maxx, int miny, int maxy) {
    boolean contains(Pair<Integer> position) {
        return (position.a() >= minx && position.a() < maxx)
                && (position.b() >= miny && position.b() < maxy);
    }
}
