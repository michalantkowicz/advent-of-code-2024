package com.adventofcode.day25;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.readFile;

class OfficeLockTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldCalculateProperCountOfKeysThatFit(String description, String file, long expected) {
        // given
        OfficeLock lock = new OfficeLock();

        // when
        long count = lock.countKeysThatFit(parseLocks(file), parseKeys(file));

        // then
        Assertions.assertThat(count).isEqualTo(expected);
    }

    private List<String> parseLocks(String file) {
        String[] inputParts = file.split("(?m)^\\s*$");
        return Arrays.stream(inputParts).map(OfficeLockTest::trimEmptyLines).filter(p -> p.lines().toList().getFirst().charAt(0) == '#').toList();
    }

    private List<String> parseKeys(String file) {
        String[] inputParts = file.split("(?m)^\\s*$");
        return Arrays.stream(inputParts).map(OfficeLockTest::trimEmptyLines).filter(p -> p.lines().toList().getLast().charAt(0) == '#').toList();
    }

    private static String trimEmptyLines(String s) {
        return s.lines().filter(l -> !l.isEmpty()).collect(Collectors.joining("\n"));
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", readFile("/day25/0.in"), 3),
                Arguments.of("1.in", readFile("/day25/1.in"), 3090)
        );
    }
}