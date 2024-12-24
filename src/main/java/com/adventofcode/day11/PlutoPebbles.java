package com.adventofcode.day11;

import com.adventofcode.common.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.LongStream;

import static java.lang.Long.parseLong;

class PlutoPebbles {
    private static class Rule {
        private final Predicate<String> isApplicable;
        private final Function<String, List<String>> process;

        Rule(Predicate<String> isApplicable, Function<String, List<String>> process) {
            this.isApplicable = isApplicable;
            this.process = process;
        }
    }

    private static final Map<Pair<String>, Long> CACHE = new HashMap<>();

    long stonesCountAfterBlinks(List<String> stones, int blinks) {
        List<Rule> rules = List.of(
                new Rule("0"::equals, _ -> Collections.singletonList("1")),
                new Rule(s -> s.length() % 2 == 0, s -> LongStream.of(firstHalf(s), secondHalf(s)).mapToObj(String::valueOf).toList()),
                new Rule(_ -> true, s -> Collections.singletonList(String.valueOf(2024L * parseLong(s))))
        );
        return stones.stream().mapToLong(s -> getListSize(s, rules, blinks)).sum();
    }

    private static Long firstHalf(String s) {
        return parseLong(s.substring(0, s.length() / 2));
    }

    private static Long secondHalf(String s) {
        return parseLong(s.substring(s.length() / 2));
    }

    private long getListSize(String stone, List<Rule> rules, int blinks) {
        Pair<String> stoneByBlink = new Pair<>(stone, String.valueOf(blinks));

        if (blinks == 0) {
            return 1;
        } else if (CACHE.containsKey(stoneByBlink)) {
            return CACHE.get(stoneByBlink);
        } else {
            Rule rule = rules.stream().filter(r -> r.isApplicable.test(stone)).findFirst().orElseThrow();
            long listSize = rule.process.apply(stone).stream().mapToLong(s -> getListSize(s, rules, blinks - 1)).sum();
            CACHE.put(stoneByBlink, listSize);
            return listSize;
        }
    }
}