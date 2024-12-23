package com.adventofcode.day23;

import com.adventofcode.common.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    String findPassword(List<String> input) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (String pair : input) {
            String a = pair.split("-")[0];
            String b = pair.split("-")[1];
            graph.computeIfAbsent(a, _ -> new HashSet<>()).add(b);
            graph.computeIfAbsent(b, _ -> new HashSet<>()).add(a);
        }

        Set<String> P = new HashSet<>(graph.keySet());
        Set<String> R = new HashSet<>();
        Set<String> X = new HashSet<>();
        List<Set<String>> results = new ArrayList<>();
        bronKerbosch(graph, results, R, P, X);

        return results.stream()
                .sorted(Comparator.comparingInt(Set<String>::size).reversed())
                .toList().getFirst().stream()
                .sorted()
                .collect(Collectors.joining(","));
    }

    private void bronKerbosch(Map<String, Set<String>> graph, List<Set<String>> results, Set<String> R, Set<String> P, Set<String> X) {
        if (P.isEmpty() && X.isEmpty()) {
            results.add(new HashSet<>(R));
        } else {
            for (String node : new HashSet<>(P)) {
                Set<String> neighbours = new HashSet<>(graph.get(node));
                Set<String> R2 = Stream.of(R, Set.of(node)).flatMap(Set::stream).collect(Collectors.toSet());
                Set<String> P2 = P.stream().filter(neighbours::contains).collect(Collectors.toSet());
                Set<String> X2 = X.stream().filter(neighbours::contains).collect(Collectors.toSet());

                bronKerbosch(graph, results, R2, P2, X2);

                P.remove(node);
                X.add(node);
            }
        }
    }
}
