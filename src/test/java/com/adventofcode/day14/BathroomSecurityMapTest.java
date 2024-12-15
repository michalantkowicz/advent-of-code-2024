package com.adventofcode.day14;

import com.adventofcode.common.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static java.lang.Integer.parseInt;

class BathroomSecurityMapTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperSecurityFactor(String description, String input, Pair<Integer> mapSize, long expected) {
        // given
        BathroomSecurityMap map = new BathroomSecurityMap();
        List<Robot> robots = parseRobots(input);

        // when
        long securityFactor = map.calculateSecurityFactor(robots, new Quadrant(0, mapSize.a(), 0, mapSize.b()));

        // then
        Assertions.assertThat(securityFactor).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldDrawFirstEasterEgg(String description, String input, Pair<Integer> mapSize, long expected) {
        // given
        BathroomSecurityMap map = new BathroomSecurityMap();
        List<Robot> robots = parseRobots(input);

        // when
        int secondOfFirstEasterEgg = map.drawFirstEasterEgg(robots, new Quadrant(0, mapSize.a(), 0, mapSize.b()));

        // then
        Assertions.assertThat(secondOfFirstEasterEgg).isEqualTo(expected);
    }

    private List<Robot> parseRobots(String input) {
        List<Robot> robots = new ArrayList<>();
        input.lines().forEach(line -> {
            String p = line.split(" ")[0].split("=")[1];
            String v = line.split(" ")[1].split("=")[1];
            Pair<Integer> position = new Pair<>(parseInt(p.split(",")[0]), parseInt(p.split(",")[1]));
            Pair<Integer> velocity = new Pair<>(parseInt(v.split(",")[0]), parseInt(v.split(",")[1]));
            robots.add(new Robot(position, velocity));
        });
        return robots;
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day14/0.in"), new Pair<>(11, 7), 12),
                Arguments.of("1.in", readFile("/day14/1.in"), new Pair<>(101, 103), 228690000)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("1.in", readFile("/day14/1.in"), new Pair<>(101, 103), 7093)
        );
    }
}