package com.adventofcode.day17;

import com.adventofcode.common.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

class OperationComputer {
    Map<String, Long> registers = new HashMap<>();
    List<Long> output = new ArrayList<>();
    List<BiFunction<Integer, Integer, Integer>> instructions = new ArrayList<>(List.of(
            /* 0:adv */ (i, x) -> {
                registers.put("A", registers.get("A") / (int) (Math.pow(2, getComboOperand(x))));
                return i + 1;
            },
            /* 1:bxl */ (i, x) -> {
                registers.put("B", registers.get("B") ^ x);
                return i + 1;
            },
            /* 2:bst */ (i, x) -> {
                registers.put("B", getComboOperand(x) % 8);
                return i + 1;
            },
            /* 3:jnz */ (i, x) -> registers.get("A").equals(0L) ? i + 1 : x,
            /* 4:bxc */ (i, x) -> {
                registers.put("B", registers.get("B") ^ registers.get("C"));
                return i + 1;
            },
            /* 5:out */ (i, x) -> {
                output.add(getComboOperand(x) % 8);
                return i + 1;
            },
            /* 6:bdv */ (i, x) -> {
                registers.put("B", registers.get("A") / (int) (Math.pow(2, getComboOperand(x))));
                return i + 1;
            },
            /* 7:bdv */ (i, x) -> {
                registers.put("C", registers.get("A") / (int) (Math.pow(2, getComboOperand(x))));
                return i + 1;
            }
    ));

    String getOutput(Map<String, Long> registers, List<Pair<Integer>> input) {
        this.registers.putAll(registers);
        output.clear();
        int index = 0;
        while (index < input.size()) {
            index = instructions.get(input.get(index).a()).apply(index, input.get(index).b());
        }
        return output.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    private long getComboOperand(long i) {
        return switch ((int) i) {
            case 0, 1, 2, 3 -> i;
            case 4 -> this.registers.get("A");
            case 5 -> this.registers.get("B");
            case 6 -> this.registers.get("C");
            default -> throw new IllegalArgumentException("Unexpected operand: " + i);
        };
    }
}