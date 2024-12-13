package com.adventofcode.day13;

import java.util.List;

class ArcadeGames {
    public long calculateMinimalCost(List<Arcade> arcades) {
        long cost = 0;
        for (Arcade arcade : arcades) {
            Long minimalCost = null;
            for (long i = 1; i <= (long) Math.ceil(arcade.prize().a() / arcade.A().a()); i++) {
                for (long j = 0; j <= (long) Math.ceil(arcade.prize().a() / arcade.B().a()); j++) {
                    long a = i * arcade.A().a() + j * arcade.B().a();
                    long b = i * arcade.A().b() + j * arcade.B().b();
                    if (a > arcade.prize().a() || b > arcade.prize().b()) {
                        break;
                    } else {
                        if (a == arcade.prize().a() && b == arcade.prize().b()) {
                            if (minimalCost == null || i * 3 + j < minimalCost) {
                                minimalCost = i * 3 + j;
                            }
                        }
                    }
                }
            }
            if (minimalCost != null) {
                cost += minimalCost;
            }
        }
        return cost;
    }
}
