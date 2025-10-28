package racingcar.dto.assembler;

import java.util.List;
import racingcar.dto.LapSnapshotGroup;
import racingcar.dto.RaceResult;

/**
 * 레이스 히스토리와 우승자 리스트로 RaceResult를 생성하는 어셈블러 클래스입니다.
 */
public final class RaceResultAssembler {

    // ===== Static Methods =====

    /**
     * 주어진 스냅샷 그룹과 우승자 목록으로 RaceResult를 생성합니다.
     *
     * @param history 전체 경기 히스토리 (LapSnapshotGroup 목록)
     * @param winners 우승자 이름 목록
     * @return RaceResult 인스턴스
     */
    public static RaceResult assemble(List<LapSnapshotGroup> history, List<String> winners) {
        return new RaceResult(history, winners);
    }

    // ===== Constructor =====

    private RaceResultAssembler() {
    }
}
