package racingcar.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.domain.Car;
import racingcar.domain.Engine;
import racingcar.domain.FixedLengthTrack;
import racingcar.domain.Track;
import java.util.List;
import racingcar.game.RacingGame;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RacingGameTest {
    private static final Engine ALWAYS_MOVE = () -> true;
    private static final Engine NEVER_MOVE = () -> false;
    private static final String CAR_NAME = "car";
    private static final Track TRACK_1 = new FixedLengthTrack(1);
    private static final int VALID_ATTEMPTS = 1;

    @Nested
    @DisplayName("생성 시 검증")
    class InitializationValidation {

        @ParameterizedTest
        @ValueSource(ints = {0, -1, -2})
        @DisplayName("시도 횟수가 1 미만이면 예외가 발생한다")
        void throwsIfAttemptsInvalid(int invalidAttempts) {
            // Given
            Car car = new Car(CAR_NAME, ALWAYS_MOVE);

            // When / Then
            assertThatThrownBy(() ->
                    new RacingGame(List.of(car), TRACK_1, invalidAttempts)
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("중복된 이름이 존재하면 예외가 발생한다")
        void throwsIfDuplicateCarNames() {
            // Given
            Car c1 = new Car("dup", NEVER_MOVE);
            Car c2 = new Car("dup", NEVER_MOVE);

            // When / Then
            assertThatThrownBy(() ->
                    new RacingGame(List.of(c1, c2), TRACK_1, VALID_ATTEMPTS)
            ).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("게임 진행")
    class PlayBehavior {

        @ParameterizedTest
        @CsvSource( value = {"1, 1", "1, 5", "5, 1"})
        @DisplayName("ready()는 모든 상태를 초기화한다")
        void readyResetsProgress(int attempts, int trackLength) {
            // Given
            Car car = new Car(CAR_NAME, ALWAYS_MOVE);
            RacingGame game = new RacingGame(List.of(car), new FixedLengthTrack(trackLength), attempts);
            game.ready();
            game.play();

            // When
            game.ready();

            // Then
            assertThat(game.getLapOf(CAR_NAME)).isZero();
            assertThat(game.getDistanceInLapOf(CAR_NAME)).isZero();
        }

        @ParameterizedTest
        @CsvSource( value = {"3, 5", "5, 3", "7, 10", "10, 7"})
        @DisplayName("항상 전진하는 차는 lap/distance가 올바르게 갱신된다")
        void alwaysMovesAndUpdatesCorrectly(int attempts, int trackLength) {
            // Given
            Car car = new Car(CAR_NAME, ALWAYS_MOVE);
            RacingGame game = new RacingGame(List.of(car), new FixedLengthTrack(trackLength), attempts);
            game.ready();

            // When
            game.play();

            // Then
            assertThat(game.getLapOf(CAR_NAME)).isEqualTo(attempts / trackLength);
            assertThat(game.getDistanceInLapOf(CAR_NAME)).isEqualTo(attempts % trackLength);
        }

        @ParameterizedTest
        @CsvSource( value = {"3, 5", "5, 3", "7, 10", "10, 7"})
        @DisplayName("움직이지 않으면 lap/distance는 유지된다")
        void neverMovesStaysStill(int attempts, int trackLength) {
            // Given
            Car car = new Car(CAR_NAME, NEVER_MOVE);
            RacingGame game = new RacingGame(List.of(car), new FixedLengthTrack(trackLength), attempts);
            game.ready();

            // When
            game.play();

            // Then
            assertThat(game.getLapOf(CAR_NAME)).isZero();
            assertThat(game.getDistanceInLapOf(CAR_NAME)).isZero();
        }

        @Test
        @DisplayName("lap은 몫, distance는 나머지로 누적된다")
        void wrapsIntoLapCorrectly() {
            // Given
            int trackLength = 4, attempts = 9;
            Car car = new Car(CAR_NAME, ALWAYS_MOVE);
            RacingGame game = new RacingGame(List.of(car), new FixedLengthTrack(trackLength), attempts);
            game.ready();

            // When
            game.play();

            // Then
            assertThat(game.getLapOf(CAR_NAME)).isEqualTo(2);
            assertThat(game.getDistanceInLapOf(CAR_NAME)).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("우승자 판정")
    class WinnerBehavior {

        @ParameterizedTest
        @ValueSource(ints = {1, 3, 5, 7})
        @DisplayName("더 많이 달린 차가 단독 우승자가 된다")
        void singleWinnerByLap(int attempts) {
            // Given
            Car fast = new Car("fast", ALWAYS_MOVE);
            Car slow = new Car("slow", NEVER_MOVE);
            RacingGame game = new RacingGame(List.of(fast, slow), TRACK_1, attempts);
            game.ready();

            // When
            game.play();

            // Then
            assertThat(game.getWinners()).containsExactly("fast");
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 3, 5, 7})
        @DisplayName("동일하게 달리면 공동 우승자가 된다")
        void jointWinnersBySameLap(int attempts) {
            // Given
            Car c1 = new Car("alp", ALWAYS_MOVE);
            Car c2 = new Car("bra", ALWAYS_MOVE);
            Car c3 = new Car("cha", ALWAYS_MOVE);
            RacingGame game = new RacingGame(List.of(c1, c2, c3), TRACK_1, attempts);
            game.ready();

            // When
            game.play();

            // Then
            assertThat(game.getWinners()).containsExactly("alp", "bra", "cha");
        }
    }
}
