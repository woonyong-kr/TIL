package racingcar.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CarTest {

    @Nested
    @DisplayName("자동차 이름")
    public class NameValidation {

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "Rolls-Royce", "Lamborghini"})
        @DisplayName("1자 미만 또는 5자 초과면 예외가 발생한다")
        void invalidLength_throwsException(String invalidName) {
            // When & Then
            assertThatThrownBy(() -> new Car(invalidName, () -> true))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"Kia", "Tesla", "Volvo", "Audi"})
        @DisplayName("이름이 5자 이하면 생성에 성공한다")
        void validLength_createsCar(String validName) {
            // When & Then: 정상 생성
            assertThatCode(() -> new Car(validName, () -> true))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("자동차 이동")
    public class Movement {

        @Test
        @DisplayName("이동 조건을 만족하면 거리 +1 증가한다")
        void movesIfConditionMet() {
            // Given: 항상 이동하는 자동차
            Car car = new Car("woo", () -> true);

            // When
            car.move();

            // Then
            assertThat(car.getDistance()).isEqualTo(1);
        }

        @Test
        @DisplayName("조건 불만족 시 거리를 유지한다")
        void doesNotMoveIfBlocked() {
            // Given: 절대 이동하지 않는 자동차
            Car car = new Car("woo", () -> false);

            // When
            car.move();

            // Then
            assertThat(car.getDistance()).isZero();
        }
    }
}

