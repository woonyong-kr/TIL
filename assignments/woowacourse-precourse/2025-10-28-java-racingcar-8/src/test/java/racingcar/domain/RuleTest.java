package racingcar.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RuleTest {
    private static final int MOVING_THRESHOLD = 4;

    @Nested
    @DisplayName("Rule 판단 조건")
    public class RuleBehavior {

        @ParameterizedTest
        @ValueSource(ints = {4, 5, 6, 7, 8, 9})
        @DisplayName("입력값이 4 이상이면 true를 반환한다")
        void returnsTrueWhenInputIsGreaterThanOrEqualTo4(int validInput) {
            // Given
            Rule rule = randomNumber -> randomNumber >= MOVING_THRESHOLD;

            // When
            boolean result = rule.isSatisfiedBy(validInput);

            // Then
            assertThat(result).isTrue();
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        @DisplayName("입력값이 4 미만이면 false를 반환한다")
        void returnsFalseWhenInputIsLessThan4(int invalidInput) {
            // Given
            Rule rule = randomNumber -> randomNumber >= MOVING_THRESHOLD;

            // When
            boolean result = rule.isSatisfiedBy(invalidInput);

            // Then
            assertThat(result).isFalse();
        }
    }
}
