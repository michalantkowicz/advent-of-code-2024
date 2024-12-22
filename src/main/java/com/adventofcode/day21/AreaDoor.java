package com.adventofcode.day21;

import java.util.List;

class AreaDoor {
    long calculateComplexity(List<String> codes) {
        long complexity = 0;
        for (String code : codes) {
            Keypad numeric = new NumericKeypad();
            Keypad remote = new DirectionalKeypad(numeric);
            Keypad manual = new DirectionalKeypad(remote);
            List<String> result = manual.process(code);
            complexity += (result.size() * Long.parseLong(code.replace("A", "")));
        }
        return complexity;
    }
}
