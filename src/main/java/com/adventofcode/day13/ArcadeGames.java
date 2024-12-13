package com.adventofcode.day13;

import com.adventofcode.common.Pair;

import java.util.List;

class ArcadeGames {
    public long calculateMinimalCost(List<Arcade> arcades) {
        long cost = 0;
        for (Arcade arcade : arcades) {
            long parallelogramArea = parallelogramArea(arcade.B(), arcade.prize());
            long subParallelogramArea = parallelogramArea(arcade.B(), arcade.A());
            if (parallelogramArea % subParallelogramArea == 0) {
                long aMul = parallelogramArea / subParallelogramArea;
                long bTotal = arcade.prize().a() - (aMul * arcade.A().a());
                if (bTotal % arcade.B().a() == 0) {
                    long bMul = bTotal / arcade.B().a();
                    cost += bMul + 3 * aMul;
                }
            }
        }
        return cost;
    }

    private static long parallelogramArea(Pair<Long> first, Pair<Long> second) {
        return Math.abs(first.a() * second.b() - first.b() * second.a());
    }
}
