package com.adventofcode.day17;

import com.adventofcode.common.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

class OperationComputerTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperOutput(String description, String input, String expected) {
        // given
        OperationComputer computer = new OperationComputer();
        String[] inputParts = input.split("(?m)^\\s*$");
        Map<String, Long> registers = parseRegisters(inputParts[0]);
        List<Pair<Integer>> operationsWithOperands = parseOperations(inputParts[1]);

        // when
        String output = computer.getOutput(registers, operationsWithOperands);

        // then
        Assertions.assertThat(output).isEqualTo(expected);
    }

    @Disabled("[day17 part 2] Running longer than 1s, run manually if necessary")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperAValue(String description, String input, long expected) {
        // given
        OperationComputer computer = new OperationComputer();
        String[] inputParts = input.split("(?m)^\\s*$");
        Map<String, Long> registers = parseRegisters(inputParts[0]);
        List<Pair<Integer>> operationsWithOperands = parseOperations(inputParts[1]);

        // when
        long aValue = computer.findAValue(registers, operationsWithOperands);
        System.out.println(aValue);
        // then
        Assertions.assertThat(aValue).isEqualTo(expected);
    }

    private Map<String, Long> parseRegisters(String input) {
        Map<String, Long> registers = new HashMap<>();
        List<String> lines = input.lines().toList();
        registers.put("A", parseLong(lines.get(0).split(": ")[1]));
        registers.put("B", parseLong(lines.get(1).split(": ")[1]));
        registers.put("C", parseLong(lines.get(2).split(": ")[1]));
        return registers;
    }

    private List<Pair<Integer>> parseOperations(String input) {
        List<Pair<Integer>> result = new ArrayList<>();
        String[] numbers = input.split(" ")[1].split(",");
        for (int i = 0; i < numbers.length; i += 2) {
            result.add(new Pair<>(parseInt(numbers[i]), parseInt(numbers[i + 1])));
        }
        return result;
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0_1.in", readFile("/day17/0_1.in"), "4,2,5,6,7,7,7,7,3,1,0"),
                Arguments.of("0.in", readFile("/day17/0.in"), "4,6,3,5,6,3,5,2,1,0"),
                Arguments.of("1.in", readFile("/day17/1.in"), "4,0,4,7,1,2,7,1,6")
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
//                Arguments.of("0_2.in", readFile("/day17/0_2.in"), 117440L),
                Arguments.of("1.in", readFile("/day17/1.in"), -1L)
        );
    }
}