package com.adventofcode.day8;

import com.adventofcode.common.StringMatrix;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class RoofAntennaTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void antinodesCountIsValid(String description, StringMatrix map, long expected) {
        // given
        RoofAntenna antenna = new RoofAntenna();

        // when
        long count = antenna.countAntinodes(map);

        // then
        assertThat(count).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void antinodesCountIsValidWithUpdatedModel(String description, StringMatrix map, long expected) {
        // given
        RoofAntenna antenna = new RoofAntenna();

        // when
        long count = antenna.countAntinodesWithUpdatedModel(map);

        // then
        assertThat(count).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", new StringMatrix(readFile("/day8/0.in")), 14),
                Arguments.of("1.in", new StringMatrix(readFile("/day8/1.in")), 348)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0_2.in", new StringMatrix(readFile("/day8/0_2.in")), 9),
                Arguments.of("0.in", new StringMatrix(readFile("/day8/0.in")), 34),
                Arguments.of("1.in", new StringMatrix(readFile("/day8/1.in")), 1221)
        );
    }
}