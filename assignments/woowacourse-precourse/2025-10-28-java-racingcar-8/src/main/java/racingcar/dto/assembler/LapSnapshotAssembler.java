package racingcar.dto.assembler;

import java.util.List;
import racingcar.dto.LapSnapshot;
import racingcar.dto.LapSnapshotGroup;
import racingcar.game.RacingLapState;

/**
 * LapRecord 리스트를 LapSnapshotGroup으로 변환하는 어셈블러 클래스입니다.
 */
public class LapSnapshotAssembler {

    // ===== Static Methods =====

    /**
     * 주어진 LapRecord 리스트로부터 LapSnapshotGroup을 생성합니다.
     *
     * @param records 랩 기록 목록
     * @return LapSnapshotGroup 인스턴스
     */
    public static LapSnapshotGroup assemble(List<RacingLapState> records) {
        List<LapSnapshot> snapshots = records.stream()
                .map(record -> new LapSnapshot(record.getName(), record.getLap()))
                .toList();

        return new LapSnapshotGroup(snapshots);
    }

    // ===== Constructor =====

    private LapSnapshotAssembler() {
    }
}
