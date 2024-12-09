package com.adventofcode.day9;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class DiskFragmenterTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void checksumIsValid(String description, String blocksString, long expected) {
        // given
        DiskFragmenter diskFragmenter = new DiskFragmenter();
        List<Integer> blocks = Arrays.stream(blocksString.split("")).map(Integer::parseInt).toList();

        // when
        long checksum = diskFragmenter.compactAndGetChecksum(new ArrayList<>(blocks));

        // then
        assertThat(checksum).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0_1.in", readFile("/day9/0_1.in"), 60),
                Arguments.of("0.in", readFile("/day9/0.in"), 1928),
                Arguments.of("1.in", readFile("/day9/1.in"), 6301895872542L)
        );
    }
}