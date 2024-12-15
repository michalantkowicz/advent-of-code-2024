package com.adventofcode.day14;

import com.adventofcode.common.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class BathroomSecurityMap {
    long calculateSecurityFactor(List<Robot> robots, Quadrant map) {
        List<Quadrant> quadrants = List.of(
                new Quadrant(0, map.maxx() / 2, 0, map.maxy() / 2),
                new Quadrant(map.maxx() / 2 + 1, map.maxx(), 0, map.maxy() / 2),
                new Quadrant(0, map.maxx() / 2, map.maxy() / 2 + 1, map.maxy()),
                new Quadrant(map.maxx() / 2 + 1, map.maxx(), map.maxy() / 2 + 1, map.maxy())
        );
        List<Robot> simulatedRobots = robots.stream().map(r -> simulate(r, 100, map)).toList();
        return quadrants.stream()
                .map(q -> simulatedRobots.stream().filter(r -> q.contains(r.position())).count())
                .reduce(1L, (a, b) -> a * b);
    }

    private static Robot simulate(Robot robot, int times, Quadrant map) {
        Pair<Integer> totalVelocity = mul(times, robot.velocity());
        Pair<Integer> p = new Pair<>(
                (totalVelocity.a() + robot.position().a()) % map.maxx(),
                (totalVelocity.b() + robot.position().b()) % map.maxy()
        );
        p = new Pair<>(p.a() < 0 ? map.maxx() + p.a() : p.a(), p.b() < 0 ? map.maxy() + p.b() : p.b());
        return new Robot(p, robot.velocity());
    }

    private static Pair<Integer> mul(int x, Pair<Integer> second) {
        return new Pair<>(x * second.a(), x * second.b());
    }

    int drawFirstEasterEgg(List<Robot> robots, Quadrant map) {
        int result = 0;
        for (int t = 0; t < Integer.MAX_VALUE; t++) {
            int finalT = t;
            List<Robot> simulatedRobots = robots.stream().map(r -> simulate(r, finalT, map)).toList();

            Map<Integer, List<Pair<Integer>>> collect = simulatedRobots.stream().map(Robot::position).collect(Collectors.groupingBy(Pair::b));
            int maxInRow = 0;
            for (List<Pair<Integer>> row : collect.values()) {
                int tempMax = 0;
                List<Integer> sorted = row.stream().map(Pair::a).sorted().toList();
                for (int i = 1; i < sorted.size(); i++) {
                    if (sorted.get(i) - 1 == sorted.get(i - 1)) {
                        tempMax++;
                    } else {
                        if (tempMax > maxInRow) {
                            maxInRow = tempMax;
                            tempMax = 0;
                        }
                    }
                }
            }

            if (maxInRow > 20) {
                result = t;
//                System.out.println(t);
//                printOut(map, simulatedRobots);
                break;
            }
        }
        return result;
    }

//    private static void printOut(Quadrant map, List<Robot> simulatedRobots) {
//        for (int i = 0; i < map.maxy(); i++) {
//            for (int j = 0; j < map.maxx(); j++) {
//                int finalA = j;
//                int finalB = i;
//                long count = simulatedRobots.stream().filter(r -> r.position().a() == finalA && r.position().b() == finalB).count();
//                if (count > 0) {
//                    System.out.print(count);
//                } else {
//                    System.out.print(".");
//                }
//            }
//            System.out.print("\n");
//        }
//    }
}