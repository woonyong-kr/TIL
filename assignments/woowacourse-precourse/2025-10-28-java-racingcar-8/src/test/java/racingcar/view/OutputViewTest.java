package racingcar.view;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.dto.RaceResult;
import racingcar.dto.LapSnapshot;
import racingcar.dto.LapSnapshotGroup;


import static org.assertj.core.api.Assertions.assertThat;

public class OutputViewTest extends NsTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 7})
    @DisplayName("회차 수만큼 '-'로 진행도 출력한다")
    void print_rendersProgressBarPerCar(int validLaps) {
        // Given
        List<String> carNames = List.of("alp", "bra", "cha", "del", "ech");
        List<LapSnapshotGroup> snapshotGroups = createDummySnapshotGroups(carNames, validLaps);
        RaceResult result = new RaceResult(snapshotGroups, List.of());

        // When
        OutputView outputView = new OutputView();
        outputView.print(result);
        String printed = output();

        // Then
        for (String carName : carNames) {
            assertThat(printed).contains(carName + " : " + "-".repeat(validLaps));
        }
    }

    @Test
    @DisplayName("우승자 리스트를 쉼표로 구분해 출력한다")
    void print_formatsWinnersAsCommaSeparatedList() {
        // Given
        List<String> winners = List.of("alp", "bra", "cha");
        RaceResult result = new RaceResult(List.of(), winners);

        // When
        OutputView outputView = new OutputView();
        outputView.print(result);

        // Then
        assertThat(output()).contains("최종 우승자 : alp, bra, cha");
    }

    // ===== 유틸 =====

    private List<LapSnapshotGroup> createDummySnapshotGroups(List<String> carNames, int laps) {
        List<LapSnapshotGroup> groups = new ArrayList<>();
        for (int i = 0; i < laps; i++) {
            List<LapSnapshot> snapshots = new ArrayList<>();
            for (String carName : carNames) {
                snapshots.add(new LapSnapshot(carName, i + 1));
            }
            groups.add(new LapSnapshotGroup(snapshots));
        }
        return groups;
    }

    @Override
    protected void runMain() {

    }
}
