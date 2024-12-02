package com.adventofcode.day1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getIntColumn;
import static org.assertj.core.api.Assertions.assertThat;

class LocationListsTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void checkDistances(String description, List<Integer> a, List<Integer> b, long expected) {
        // given
        LocationLists locationLists = new LocationLists();

        // when
        long sum = locationLists.sumDistances(a, b);

        // then
        assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSecondInput")
    void checkSimilarity(String description, List<Integer> a, List<Integer> b, long expected) {
        // given
        LocationLists locationLists = new LocationLists();

        // when
        long similarity = locationLists.getSimilarity(a, b);

        // then
        assertThat(similarity).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", getIntColumn("/day1/0.in", 0), getIntColumn("/day1/0.in", 1), 11),
                Arguments.of("1.in", getIntColumn("/day1/1.in", 0), getIntColumn("/day1/1.in", 1), 1110981)
        );
    }

    private static Stream<Arguments> provideSecondInput() {
        return Stream.of(
                Arguments.of("0.in", getIntColumn("/day1/0.in", 0), getIntColumn("/day1/0.in", 1), 31),
                Arguments.of("1.in", getIntColumn("/day1/1.in", 0), getIntColumn("/day1/1.in", 1), 24869388)
        );
    }
}