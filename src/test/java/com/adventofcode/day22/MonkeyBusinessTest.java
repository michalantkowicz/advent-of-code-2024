package com.adventofcode.day22;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.TestUtils.getLines;

class MonkeyBusinessTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideFirstInput")
    void shouldCalculateProperSecrets(String description, List<Long> secrets, long expected) {
        // given
        MonkeyBusiness business = new MonkeyBusiness();

        // when
        long sum = business.sum2000Generations(secrets);

        // then
        Assertions.assertThat(sum).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFirstInput() {
        return Stream.of(
                Arguments.of("0.in", getLines("/day22/0.in").stream().map(Long::parseLong).toList(), 37327623),
                Arguments.of("1.in", getLines("/day22/1.in").stream().map(Long::parseLong).toList(), 18941802053L)
        );
    }
}