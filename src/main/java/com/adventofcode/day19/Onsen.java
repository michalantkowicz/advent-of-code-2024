package com.adventofcode.day19;

import java.util.Collections;
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
}
