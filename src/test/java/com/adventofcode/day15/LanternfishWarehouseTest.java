package com.adventofcode.day15;

import com.adventofcode.common.Direction;
import com.adventofcode.common.StringMatrix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;

class LanternfishWarehouseTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperSumOfCoordinates(String description, String input, long expected) {
        // given
        LanternfishWarehouse warehouse = new LanternfishWarehouse();
        String[] inputParts = input.split("(?m)^\\s*$");
        StringMatrix map = parseMap(inputParts[0]);
        List<Direction> directions = parseDirections(inputParts[1]);

        // when
        long sum = warehouse.getBoxesCoordinatesSum(map, directions);

        // then
        Assertions.assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperSumOfCoordinatesForWideMap(String description, String input, long expected) {
        // given
        LanternfishWarehouse warehouse = new LanternfishWarehouse();
        String[] inputParts = input.split("(?m)^\\s*$");
        StringMatrix map = extendMap(inputParts[0]);
        List<Direction> directions = parseDirections(inputParts[1]);

        // when
        long sum = warehouse.getBoxesCoordinatesSum(map, directions);

        // then
        Assertions.assertThat(sum).isEqualTo(expected);
    }

    private StringMatrix parseMap(String input) {
        return new StringMatrix(input);
    }

    private StringMatrix extendMap(String input) {
        StringBuilder extended = new StringBuilder();
        for (String line : input.lines().toList()) {
            for (String c : line.split("")) {
                extended.append(switch (c) {
                    case "#" -> "##";
                    case "O" -> "[]";
                    case "." -> "..";
                    case "@" -> "@.";
                    default -> throw new IllegalArgumentException("Unexpected argument: [" + c + "]");
                });
            }
            extended.append("\n");
        }
        return new StringMatrix(extended.toString());
    }

    private List<Direction> parseDirections(String input) {
        List<Direction> directions = new ArrayList<>();
        for (String c : input.split("")) {
            if (!c.isBlank()) {
                directions.add(
                        switch (c) {
                            case ">" -> Direction.RIGHT;
                            case "v" -> Direction.DOWN;
                            case "<" -> Direction.LEFT;
                            case "^" -> Direction.TOP;
                            default -> throw new IllegalArgumentException("Unexpected argument: [" + c + "]");
                        }
                );
            }
        }
        return directions;
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0_1.in", readFile("/day15/0_1.in"), 2028),
                Arguments.of("0.in", readFile("/day15/0.in"), 10092),
                Arguments.of("1.in", readFile("/day15/1.in"), 1371036)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0_2.in", readFile("/day15/0_2.in"), 618),
                Arguments.of("0.in", readFile("/day15/0.in"), 9021),
                Arguments.of("1.in", readFile("/day15/1.in"), 1392847)
        );
    }
}