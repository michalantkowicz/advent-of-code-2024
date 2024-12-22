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
        long complexity = door.calculateComplexity(codes);

        // then
        Assertions.assertThat(complexity).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", getLines("/day21/0.in"), 126384),
                Arguments.of("1.in", getLines("/day21/1.in"), 182844)
        );
    }
}