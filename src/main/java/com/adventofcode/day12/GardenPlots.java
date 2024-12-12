package com.adventofcode.day12;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.adventofcode.common.Direction.*;
import static java.util.stream.IntStream.rangeClosed;

class GardenPlots {
    private record Region(String type, Set<Pair<Integer>> fields, long area, long perimeter) {
        private long getCost() {
            return area * perimeter;
        }
    }

    long calculateTotalCost(StringMatrix garden) {
        Map<String, Set<Region>> regionsByType = getRegionsByType(garden);
        return regionsByType.values().stream().flatMap(Set::stream).mapToLong(Region::getCost).sum();
    }

    long calculateTotalCostAfterDiscount(StringMatrix garden) {
        Map<String, Set<Region>> regionsByType = getRegionsByType(garden);
        long costAfterDiscount = 0;
        for (Region region : regionsByType.values().stream().flatMap(Set::stream).toList()) {
            costAfterDiscount += region.area * calculateSidesCount(garden, region);
        }
        return costAfterDiscount;
    }

    private Map<String, Set<Region>> getRegionsByType(StringMatrix garden) {
        Set<Pair<Integer>> visited = new HashSet<>();
        Map<String, Set<Region>> regionsByType = new HashMap<>();

        while (visited.size() < (garden.height() * garden.width())) {
            Pair<Integer> start = garden.streamIndices().filter(p -> !visited.contains(p)).findFirst().orElseThrow();
            Region region = getRegion(garden, start, visited);
            regionsByType.computeIfAbsent(region.type, _ -> new HashSet<>()).add(region);
        }
        return regionsByType;
    }

    private Region getRegion(StringMatrix garden, Pair<Integer> start, Set<Pair<Integer>> visited) {
        long area = 0, perimeter = 0;
        String type = garden.at(start);
        Set<Pair<Integer>> fields = new HashSet<>();
        Set<Pair<Integer>> Q = new HashSet<>(List.of(start));
        while (!Q.isEmpty()) {
            Pair<Integer> position = Q.iterator().next();
            Q.remove(position);
            fields.add(position);
            visited.add(position);
            Q.addAll(getAllNeighbours(garden, type, position, visited));
            area++;
            perimeter += calculatePerimeter(garden, position);
        }
        return new Region(type, fields, area, perimeter);
    }

    private static Set<Pair<Integer>> getAllNeighbours(StringMatrix garden, String type, Pair<Integer> position, Set<Pair<Integer>> visited) {
        return Arrays.stream(Direction.values())
                .map(d -> d.moveFrom(position))
                .filter(p -> !visited.contains(p) && type.equals(garden.at(p)))
                .collect(Collectors.toSet());
    }

    private static long calculatePerimeter(StringMatrix garden, Pair<Integer> position) {
        return Arrays.stream(Direction.values())
                .map(d -> d.moveFrom(position))
                .filter(p -> garden.at(p) == null || !garden.at(p).equals(garden.at(position)))
                .count();
    }

    private static long calculateSidesCount(StringMatrix garden, Region region) {
        long sidesCount = 0;
        int min_x = minBy(region.fields, Pair::a);
        int min_y = minBy(region.fields, Pair::b);
        int max_x = maxBy(region.fields, Pair::a);
        int max_y = maxBy(region.fields, Pair::b);
        sidesCount += countSides(garden, region, rangeClosed(min_x, max_x), Pair::a, Pair::b, LEFT);
        sidesCount += countSides(garden, region, rangeClosed(min_x, max_x + 1), Pair::a, Pair::b, RIGHT);
        sidesCount += countSides(garden, region, rangeClosed(min_y, max_y), Pair::b, Pair::a, TOP);
        sidesCount += countSides(garden, region, rangeClosed(min_y, max_y + 1), Pair::b, Pair::a, DOWN);
        return sidesCount;
    }

    private static <T> int minBy(Collection<T> collection, ToIntFunction<T> mapper) {
        return collection.stream().mapToInt(mapper).min().orElse(Integer.MAX_VALUE);
    }

    private static <T> int maxBy(Collection<T> collection, ToIntFunction<T> mapper) {
        return collection.stream().mapToInt(mapper).max().orElse(Integer.MIN_VALUE);
    }

    private static long countSides(StringMatrix garden, Region region, IntStream range, ToIntFunction<Pair<Integer>> compareBy,
                                   ToIntFunction<Pair<Integer>> sortBy, Direction directionToCheck) {
        return range.mapToLong(x -> countExclusiveRanges(
                region.fields.stream()
                        .filter(f -> (compareBy.applyAsInt(f) == x) && doesNotHaveNeighbour(garden, f, directionToCheck))
                        .sorted(Comparator.comparingInt(sortBy))
                        .map(sortBy::applyAsInt)
                        .toList()
        )).sum();
    }

    private static boolean doesNotHaveNeighbour(StringMatrix garden, Pair<Integer> p, Direction d) {
        return !garden.at(p).equals(garden.at(d.moveFrom(p)));
    }

    private static long countExclusiveRanges(List<Integer> numbers) {
        if (numbers.size() <= 1) {
            return numbers.size();
        } else {
            long lines = 1;
            for (int i = 1; i < numbers.size(); i++) {
                if (numbers.get(i) - numbers.get(i - 1) > 1) {
                    lines++;
                }
            }
            return lines;
        }
    }
}
