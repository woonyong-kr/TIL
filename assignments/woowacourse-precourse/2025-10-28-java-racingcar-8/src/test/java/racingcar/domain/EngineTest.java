package racingcar.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import camp.nextstep.edu.missionutils.Randoms;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;

public class EngineTest {
    private static final int LOWER_BOUND = 0;
    private static final int UPPER_BOUND = 9;
    private static final int MOVING_THRESHOLD = 4;
    private static final int RANDOM_TEST_REPEAT = 1_000;

    @Nested
    @DisplayName("Engine 동작 검증")
    public class EngineBehavior {

        @ParameterizedTest
        @ValueSource(ints = {4, 5, 6, 7, 8, 9})
        @DisplayName("난수가 4 이상이면 true를 반환한다")
        void returnsTrueWhenNumberIsGreaterThanOrEqualTo4(int validRandomNumber) {
            assertRandomNumberInRangeTest(
                    () -> {
                        // Given
                        Engine engine = () -> Randoms.pickNumberInRange(LOWER_BOUND, UPPER_BOUND) >= MOVING_THRESHOLD;

                        // When
                        boolean result = engine.isMovable();

                        // Then
                        assertThat(result).isTrue();
                    },
                    validRandomNumber
            );
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        @DisplayName("난수가 4 미만이면 false를 반환한다")
        void returnsFalseWhenNumberIsLessThan4(int invalidRandomNumber) {
            assertRandomNumberInRangeTest(
                    () -> {
                        // Given
                        Engine engine = () -> Randoms.pickNumberInRange(LOWER_BOUND, UPPER_BOUND) >= MOVING_THRESHOLD;

                        // When
                        boolean result = engine.isMovable();

                        // Then
                        assertThat(result).isFalse();
                    },
                    invalidRandomNumber
            );
        }

        @Test
        @DisplayName("Randoms.pickNumberInRange(0, 9)은 0~9 사이의 수를 반환한다")
        void pickNumberInRangeReturnsWithinBounds() {
            // When / Then
            for (int i = 0; i < RANDOM_TEST_REPEAT; i++) {
                int number = Randoms.pickNumberInRange(LOWER_BOUND, UPPER_BOUND);
                assertThat(number).isBetween(0, 9);
            }
        }
    }
}
