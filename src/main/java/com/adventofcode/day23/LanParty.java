package com.adventofcode.day23;

import com.adventofcode.common.Pair;

import java.util.*;
import java.util.stream.Collectors;

class LanParty {
    List<String> cycles = new ArrayList<>();
    Set<Pair<String>> visitedPairs = new HashSet<>();

    long countGroups(List<String> input) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (String pair : input) {
            String a = pair.split("-")[0];
            String b = pair.split("-")[1];
            graph.computeIfAbsent(a, _ -> new HashSet<>()).add(b);
            graph.computeIfAbsent(b, _ -> new HashSet<>()).add(a);
        }

        Set<String> groups = new HashSet<>();

        for (String node : graph.keySet()) {
            for (String neighbour : graph.get(node)) {
                if (visitedPairs.contains(new Pair<>(node, neighbour)) || visitedPairs.contains(new Pair<>(neighbour, node))) {
                    continue;
                }
                visitedPairs.add(new Pair<>(node, neighbour));
                getCycles(graph, node, neighbour, node, 2);
                for (String cycle : cycles) {
                    if (cycle.split(",").length == 3) {
                        groups.add(Arrays.stream(cycle.split(",")).sorted().collect(Collectors.joining(",")));
                    }
                }
            }
        }

        return groups.stream().filter(s -> s.startsWith("t") || s.contains(",t")).count();
    }

    private void getCycles(Map<String, Set<String>> graph, String node, String neighbour, String path, int depthLimit) {
        if (depthLimit == 0) {
            if (neighbour.equals(node)) {
                cycles.add(path);
            }
        } else {
            for (String n : graph.get(neighbour)) {
                getCycles(graph, node, n, path + "," + neighbour, depthLimit - 1);
            }
        }
    }
}
