package com.adventofcode.day21;

import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DirectionalKeypad extends Keypad {
    private final static StringMatrix matrix = new StringMatrix("#^A\n<v>");

    private final Keypad keypad;

    DirectionalKeypad(Keypad keypad) {
        super(matrix, new Pair<>(2, 0));
        this.keypad = keypad;
    }

    @Override
    List<String> process(String input) {
        List<String> result = new ArrayList<>();
        List<String> steps = keypad.process(input);
        for (String step : steps) {
            Pair<Integer> target = getPosition(step);
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
        return keypad.validate(Arrays.stream(result.toString().split("")).toList());
    }
}
