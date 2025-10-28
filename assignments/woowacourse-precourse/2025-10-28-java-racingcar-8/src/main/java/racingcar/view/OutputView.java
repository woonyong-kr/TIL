package racingcar.view;

import racingcar.dto.RaceResult;
import racingcar.dto.LapSnapshot;
import racingcar.dto.LapSnapshotGroup;

public class OutputView {

    // ===== Public Methods =====

    /**
     * 레이스 결과를 콘솔에 출력합니다.
     *
     * @param result 레이스 결과 객체
     */
    public void print(RaceResult result) {
        printTitle();
        printTimeline(result);
        printWinners(result);
    }

    // ===== Private Methods =====

    private void printTitle() {
        System.out.println("\n실행 결과");
    }

    private void printTimeline(RaceResult result) {
        for (LapSnapshotGroup group : result.timeline()) {
            for (LapSnapshot snap : group.snapshots()) {
                System.out.println(formatSnapshot(snap));
            }
            System.out.println();
        }
    }

    private String formatSnapshot(LapSnapshot snapshot) {
        return snapshot.carName() + " : " + "-".repeat(snapshot.laps());
    }

    private void printWinners(RaceResult result) {
        System.out.println("최종 우승자 : " + String.join(", ", result.winners()));
    }
}
