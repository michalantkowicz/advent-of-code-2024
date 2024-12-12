package com.adventofcode.day12;

import com.adventofcode.common.StringMatrix;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class GardenPlotsTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperCost(String description, StringMatrix garden, long expected) {
        // given
        GardenPlots gardenPlots = new GardenPlots();

        // when
        long cost = gardenPlots.calculateTotalCost(garden);

        // then
        assertThat(cost).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperCostAfterDiscount(String description, StringMatrix garden, long expected) {
        // given
        GardenPlots gardenPlots = new GardenPlots();

        // when
        long cost = gardenPlots.calculateTotalCostAfterDiscount(garden);

        // then
        assertThat(cost).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0_1a.in", readFile("/day12/0_1a.in"), 140),
                Arguments.of("0_1b.in", readFile("/day12/0_1b.in"), 772),
                Arguments.of("0.in", readFile("/day12/0.in"), 1930),
                Arguments.of("1.in", readFile("/day12/1.in"), 1424006)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0_2a.in", readFile("/day12/0_2a.in"), 80),
                Arguments.of("0_2b.in", readFile("/day12/0_2b.in"), 236),
                Arguments.of("0_2c.in", readFile("/day12/0_2c.in"), 368),
                Arguments.of("0_2d.in", readFile("/day12/0_2d.in"), 436),
                Arguments.of("0.in", readFile("/day12/0.in"), 1206),
                Arguments.of("1.in", readFile("/day12/1.in"), 858684)
        );
    }
}