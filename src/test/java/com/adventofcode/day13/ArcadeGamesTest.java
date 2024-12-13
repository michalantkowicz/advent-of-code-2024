package com.adventofcode.day13;

import com.adventofcode.common.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;

class ArcadeGamesTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperCost(String description, String input, long expected) {
        // given
        ArcadeGames games = new ArcadeGames();
        List<Arcade> arcades = parseArcades(input);

        // when
        long cost = games.calculateMinimalCost(arcades);

        // then
        assertThat(cost).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperCostForBigPrizes(String description, String input, long expected) {
        // given
        ArcadeGames games = new ArcadeGames();
        List<Arcade> arcades = parseArcades(input);
        List<Arcade> scaledArcades = arcades.stream().map(a -> new Arcade(
                a.A(), a.B(), new Pair<>(a.prize().a() + 10000000000000L, a.prize().b() + 10000000000000L)
        )).toList();

        // when
        long cost = games.calculateMinimalCost(scaledArcades);

        // then
        assertThat(cost).isEqualTo(expected);
    }

    private List<Arcade> parseArcades(String input) {
        List<Arcade> result = new ArrayList<>();
        List<Pair<Long>> buffer = new ArrayList<>();
        for (String line : input.lines().toList()) {
            if (line.isEmpty()) {
                result.add(
                        new Arcade(
                                buffer.get(0),
                                buffer.get(1),
                                buffer.get(2)
                        )
                );
                buffer.clear();
            } else if (line.startsWith("Button")) {
                String first = line.split(",")[0].split("\\+")[1];
                String second = line.split(",")[1].split("\\+")[1];
                buffer.add(new Pair<>(parseLong(first), parseLong(second)));
            } else {
                String first = line.split(",")[0].split("=")[1];
                String second = line.split(",")[1].split("=")[1];
                buffer.add(new Pair<>(parseLong(first), parseLong(second)));
            }
        }
        result.add(
                new Arcade(
                        buffer.get(0),
                        buffer.get(1),
                        buffer.get(2)
                )
        );
        return result;
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day13/0.in"), 480),
                Arguments.of("1.in", readFile("/day13/1.in"), 36571)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day13/0.in"), 875318608908L),
                Arguments.of("1.in", readFile("/day13/1.in"), 85527711500010L)
        );
    }
}