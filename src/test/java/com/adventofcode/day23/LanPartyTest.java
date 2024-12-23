package com.adventofcode.day23;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getLines;

class LanPartyTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldCalculateProperGroupsCount(String description, List<String> input, long expected) {
        // given
        LanParty lanParty = new LanParty();

        // when
        long count = lanParty.countGroups(input);

        // then
        Assertions.assertThat(count).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", getLines("/day23/0.in"), 7),
                Arguments.of("1.in", getLines("/day23/1.in"), 1378)
        );
    }
}