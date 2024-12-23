package com.adventofcode.day19;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Onsen {
    long countPossibleTowelDesigns(List<String> towels, List<String> designs) {
        Map<String, List<String>> towelsByFirstLetter = towels.stream().collect(Collectors.groupingBy(s -> s.substring(0, 1)));
        return designs.stream().filter(d -> isPossible(towelsByFirstLetter, d)).count();
    }

    private boolean isPossible(Map<String, List<String>> towelsByFirstLetter, String design) {
        List<String> bucket = towelsByFirstLetter.getOrDefault(design.substring(0, 1), Collections.emptyList());
        for (String prefix : bucket) {
            if (prefix.equals(design)) {
                return true;
            } else if (design.startsWith(prefix) && isPossible(towelsByFirstLetter, design.substring(prefix.length()))) {
                return true;
            }
        }
        return false;
    }

    Map<String, Long> CACHE = new HashMap<>();

    long countAllPossibleArrangements(List<String> towels, List<String> designs) {
        long sum = 0;
        for (String design : designs) {
            List<String> filtered = towels.stream().filter(design::contains).toList();
            for (String f : filtered) {
                if (design.startsWith(f)) {
                    long d = d(design, f, filtered);

                    sum += d;
                }
            }
        }
        return sum;
    }

    private long d(String design, String prefix, List<String> towels) {
        long result = 0;
        if (prefix.equals(design)) {
            result++;
        } else if (prefix.length() < design.length()) {
            for (String towel : towels) {
                String newPrefix = prefix + towel;
                if (design.startsWith(newPrefix) && design.length() >= newPrefix.length()) {
                    String key = design.substring(newPrefix.length());
                    if (!CACHE.containsKey(key)) {
                        CACHE.put(key, d(design, newPrefix, towels));
                    }
                    result += CACHE.get(key);
                }
            }
        }
        return result;
    }
}
