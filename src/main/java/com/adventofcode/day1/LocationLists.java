package com.adventofcode.day1;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LocationLists {
    public long sumDistances(List<Integer> a, List<Integer> b) {
        Collections.sort(a);
        Collections.sort(b);
        long result = 0;
        for (int i = 0; i < a.size(); i++) {
            result += Math.abs(a.get(i) - b.get(i));
        }
        return result;
    }

    public long getSimilarity(List<Integer> a, List<Integer> b) {
        Map<Integer, Long> occurrences = b.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return a.stream().mapToLong(i -> i * occurrences.getOrDefault(i, 0L)).sum();
    }
}
