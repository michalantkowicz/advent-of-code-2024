package com.adventofcode.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CrossedWires {
    String getColor(Operation operation) {
        return switch (operation) {
            case OR -> "red";
            case AND -> "blue";
            case XOR -> "green";
        };
    }

    long calculateNumber(Map<String, Integer> values, List<Input> inputs, boolean printout) {
        List<String> output = new ArrayList<>();
        while (!inputs.isEmpty()) {
            List<Input> leftInputs = new ArrayList<>();
            for (Input input : inputs) {
                if (isReady(values, input)) {
                    output.add(input.records().a() + " -> " + input.target() + " [color=\"" + getColor(input.operation()) + "\"]");
                    output.add(input.records().b() + " -> " + input.target() + " [color=\"" + getColor(input.operation()) + "\"]");
                    Integer a = values.get(input.records().a());
                    Integer b = values.get(input.records().b());
                    Integer result = input.operation().compute(a, b);
                    values.put(input.target(), result);
                } else {
                    leftInputs.add(input);
                }
                inputs = leftInputs;
            }
        }

        if (printout) {
            System.out.println("digraph G {");
            for (String s : output) {
                System.out.println(s);
            }
            System.out.println("}");
        }

        long result = 0;
        for (String zKey : values.entrySet().stream().filter(e -> e.getKey().startsWith("z") && e.getValue().equals(1)).map(Map.Entry::getKey).toList()) {
            int position = Integer.parseInt(zKey.split("z")[1]);
            result |= 1L << position;
        }

        return result;
    }

    private static boolean isReady(Map<String, Integer> values, Input input) {
        return values.containsKey(input.records().a()) && values.containsKey(input.records().b());
    }
}
