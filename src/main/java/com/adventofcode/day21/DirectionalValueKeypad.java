package com.adventofcode.day21;

import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.ArrayList;
import java.util.List;

class DirectionalValueKeypad extends Keypad {
    private final static StringMatrix matrix = new StringMatrix("#^A\n<v>");

    DirectionalValueKeypad() {
        super(matrix, new Pair<>(2, 0));
    }

    @Override
    List<String> process(String input) {
        List<String> result = new ArrayList<>();
        for (String step : input.split("")) {
            Pair<Integer> target = getPosition(step);
            result.addAll(paths.get(new Pair<>(currentPointer, target)));
            result.add(ENTER);
            currentPointer = target;
        }
        return result;
    }

    @Override
    String validate(List<String> input) {
        throw new UnsupportedOperationException();
    }
}
