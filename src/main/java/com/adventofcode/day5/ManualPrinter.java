package com.adventofcode.day5;

import com.adventofcode.common.Pair;

import java.util.*;
import java.util.function.Predicate;

class ManualPrinter {
    private static class Validator {
        Map<Integer, Set<Integer>> predecessors = new HashMap<>();

        private Validator(List<Pair<Integer>> rules) {
            rules.forEach(p -> predecessors.computeIfAbsent(p.b(), _ -> new HashSet<>()).add(p.a()));
        }

        private boolean isValid(List<Integer> update) {
            Set<Integer> mustNotAppear = new HashSet<>();
            for (int page : update) {
                if (mustNotAppear.contains(page)) {
                    return false;
                } else {
                    mustNotAppear.addAll(predecessors.getOrDefault(page, new HashSet<>()));
                }
            }
            return true;
        }

        private List<Integer> reorder(List<Integer> update) {
            List<Pair<Integer>> pagesWithPredecessorsCount = new ArrayList<>();
            for (int i : update) {
                int sum = 0;
                for (int j : update) {
                    if (i != j && predecessors.getOrDefault(i, new HashSet<>()).contains(j)) {
                        sum++;
                    }
                }
                pagesWithPredecessorsCount.add(new Pair<>(i, sum));
            }
            return pagesWithPredecessorsCount.stream().sorted(Comparator.comparingInt(Pair::b)).map(Pair::a).toList();
        }
    }

    long sumValidMiddleNumbers(List<Pair<Integer>> rules, List<List<Integer>> updates) {
        Validator validator = new Validator(rules);
        return updates.stream()
                .filter(validator::isValid)
                .mapToLong(l -> l.get(l.size() / 2))
                .sum();
    }

    long sumInvalidReorderedMiddleNumbers(List<Pair<Integer>> rules, List<List<Integer>> updates) {
        Validator validator = new Validator(rules);
        return updates.stream()
                .filter(Predicate.not(validator::isValid))
                .map(validator::reorder)
                .mapToLong(l -> l.get(l.size() / 2))
                .sum();
    }
}
