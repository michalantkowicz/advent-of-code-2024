package com.adventofcode.day21;

import org.assertj.core.api.Assertions;
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

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void shouldReturnProperComplexityFor25Robots(String description, List<String> codes, long expected) {
        // given
        AreaDoor door = new AreaDoor();

        // when
        long complexity = door.calculateComplexity(codes, 25);

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
                Arguments.of("1.in", getLines("/day21/1.in"), 226179529377982L)
        );
    }
}