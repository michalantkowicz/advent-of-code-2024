package com.adventofcode.day21;

import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.ArrayList;
import java.util.List;

class NumericKeypad extends Keypad {
    private final static StringMatrix matrix = new StringMatrix("789\n456\n123\n#0A");

    NumericKeypad() {
        super(matrix, new Pair<>(2, 3));
    }

    @Override
    List<String> process(String input) {
        List<String> result = new ArrayList<>();
        for (String c : input.split("")) {
            Pair<Integer> target = getPosition(c);
            result.addAll(paths.get(new Pair<>(currentPointer, target)));
            result.add(ENTER);
            currentPointer = target;
        }
        return result;
    }

    @Override
    String validate(List<String> input) {
        StringBuilder result = new StringBuilder();
        for (String i : input) {
            if (ENTER.equals(i)) {
                result.append(matrix.at(currentPointer));
            } else {
                currentPointer = getDirectionFromString(i).moveFrom(currentPointer);
            }
        }
        return result.toString();
    }
}
