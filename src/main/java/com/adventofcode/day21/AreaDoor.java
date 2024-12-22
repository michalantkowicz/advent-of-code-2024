package com.adventofcode.day21;

import com.adventofcode.common.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AreaDoor {
    private record Key(String s, int level) {
    }

    private long process(String s, int currentDepth, int maxDepth, Map<Key, Long> CACHE, List<DirectionalValueKeypad> keypads) {
        List<String> result = keypads.get(currentDepth - 1).process(s);
        if (currentDepth == maxDepth) {
            return result.size();
        } else {
            Key key = new Key(String.join("", result), currentDepth);
            if (!CACHE.containsKey(key)) {
                CACHE.put(key, result.stream().mapToLong(step -> process(step, currentDepth + 1, maxDepth, CACHE, keypads)).sum());
            }
            return CACHE.get(key);
        }
    }

    long calculateComplexity(List<String> codes, int robotCount) {
        long minComplexity = Long.MAX_VALUE;

        for(Map<Pair<Pair<Integer>>, List<String>> override : new DirectionalValueKeypad().possibleVariants()) {
            long complexity = 0;
            Map<Key, Long> CACHE = new HashMap<>();
            List<DirectionalValueKeypad> keypads = new ArrayList<>();

            for (int i = 0; i < robotCount; i++) {
                DirectionalValueKeypad k = new DirectionalValueKeypad();
                k.overridePaths(override);
                keypads.add(k);
            }

            for (String code : codes) {
                List<String> numericValue = new NumericKeypad().process(code);
                long size = numericValue.stream().mapToLong(step -> process(step, 1, robotCount, CACHE, keypads)).sum();
                complexity += size * Long.parseLong(code.replace("A", ""));
            }

            if(complexity < minComplexity) {
                minComplexity = complexity;
            }
        }

        return minComplexity;
    }
}
