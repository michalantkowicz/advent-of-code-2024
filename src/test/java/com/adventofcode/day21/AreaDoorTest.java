package com.adventofcode.day21;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getLines;

class AreaDoorTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldReturnProperComplexity(String description, List<String> codes, long expected) {
        // given
        AreaDoor door = new AreaDoor();

        // when
        long complexity = door.calculateComplexity(codes, 2);

        // then
        Assertions.assertThat(complexity).isEqualTo(expected);
    }

    @Disabled("Returns too high answer - probably moved are not optimal for 2+ robots")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperComplexityFor25Robots(String description, List<String> codes, long expected) {
        // given
        AreaDoor door = new AreaDoor();

        // when
        long complexity = door.calculateComplexity(codes, 25);
        //107361402712706 too low
        //268606017318748 too high

        // then
        Assertions.assertThat(complexity).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", getLines("/day21/0.in"), 126384),
                Arguments.of("1.in", getLines("/day21/1.in"), 182844)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("1.in", getLines("/day21/1.in"), -1)
        );
    }
}