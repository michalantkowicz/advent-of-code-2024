package com.adventofcode.day21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AreaDoor {
    private record Key(String s, int level) {
    }

    Map<Key, Long> CACHE = new HashMap<>();

    List<DirectionalValueKeypad> keypads = new ArrayList<>();

    private long process(String s, int currentDepth, int maxDepth) {
        List<String> result = keypads.get(currentDepth - 1).process(s);
        if (currentDepth == maxDepth) {
            return result.size();
        } else {
            Key key = new Key(String.join("", result), currentDepth);
            if (!CACHE.containsKey(key)) {
                CACHE.put(key, result.stream().mapToLong(step -> process(step, currentDepth + 1, maxDepth)).sum());
            }
            return CACHE.get(key);
        }
    }

    long calculateComplexity(List<String> codes, int robotCount) {
        long complexity = 0;

        for (int i = 0; i < robotCount; i++) {
            keypads.add(new DirectionalValueKeypad());
        }

        for (String code : codes) {
            List<String> numericValue = new NumericKeypad().process(code);
            long size = numericValue.stream().mapToLong(step -> process(step, 1, robotCount)).sum();
            complexity += size * Long.parseLong(code.replace("A", ""));
        }
        return complexity;
    }
}
