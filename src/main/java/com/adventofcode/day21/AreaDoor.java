package com.adventofcode.day21;

import java.util.List;

class AreaDoor {
    long calculateComplexity(List<String> codes, int robotCount) {
        long complexity = 0;
        for (String code : codes) {
            Keypad numeric = new NumericKeypad();
            Keypad remote = new DirectionalKeypad(numeric);
            for(int i = 1; i < robotCount; i++) {
                remote = new DirectionalKeypad(remote);
            }
            List<String> result = remote.process(code);
            complexity += (result.size() * Long.parseLong(code.replace("A", "")));
        }
        return complexity;
    }
}
