package racingcar.dto;

import java.util.List;
import java.util.Objects;


/**
 * 한 라운드의 전체 스냅샷 묶음입니다. null 방지를 위해 생성자에서 검증을 수행하며,
 *
 * @param snapshots 라운드에 포함된 자동차별 스냅샷 리스트
 */
public record LapSnapshotGroup(List<LapSnapshot> snapshots) {

    // ===== Constructor =====

    public LapSnapshotGroup {
        snapshots = List.copyOf(Objects.requireNonNull(snapshots, "LapSnapshot은 null일 수 없습니다."));
    }

    // ===== Getter =====

    @Override
    public List<LapSnapshot> snapshots() {
        return List.copyOf(snapshots);
    }
}
