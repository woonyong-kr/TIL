package racingcar.controller;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import racingcar.dto.RaceResult;
import racingcar.domain.Car;
import racingcar.domain.Engine;
import racingcar.dto.LapSnapshot;
import racingcar.dto.LapSnapshotGroup;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RaceDirectorTest {
    private static final Engine ALWAYS_MOVE_ENGINE = () -> true;

    @ParameterizedTest
    @CsvSource(value = {"5, 1", "7, 1", "3, 5", "5, 3", "7, 10", "10, 7"})
    @DisplayName("자동차, 시도 횟수, 트랙 길이로 레이스를 실행하면 결과가 생성된다")
    void runRace_returnsResultWithCorrectTimelineAndWinners(int attempts, int trackLength) {
        // Given
        List<Car> cars = createFixedParticipants("alp", "bra", "cha");
        RaceDirector director = new RaceDirector();

        // When
        RaceResult result = director.runRace(cars, attempts, trackLength);

        // Then: 결과 구조 검증
        assertThat(result).isNotNull();
        assertThat(result.timeline()).hasSize(attempts);

        LapSnapshotGroup firstRound = result.timeline().getFirst();
        assertThat(firstRound.snapshots())
                .extracting(LapSnapshot::carName)
                .containsExactly("alp", "bra", "cha");

        // Then: 우승자 검증
        List<String> winners = result.winners();
        assertThat(winners).isNotEmpty();
        assertThat(winners).containsExactly("alp", "bra", "cha");
    }

    // ===== 유틸 =====

    private List<Car> createFixedParticipants(String... names) {
        List<Car> cars = new ArrayList<>();
        for (String name : names) {
            cars.add(new Car(name, ALWAYS_MOVE_ENGINE));
        }
        return cars;
    }
}
