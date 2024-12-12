package com.adventofcode.day12;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.*;
import java.util.stream.Collectors;

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
            costAfterDiscount += region.area * calculateSides(region);
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

    private static long calculateSides(Region region) {
        return 0;
    }
}
