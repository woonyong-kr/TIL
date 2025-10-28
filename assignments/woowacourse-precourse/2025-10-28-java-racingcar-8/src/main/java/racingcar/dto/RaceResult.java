package racingcar.dto;

import java.util.List;

/**
 * 레이스의 결과를 담는 클래스입니다. - 각 라운드별 스냅샷 리스트(timeline) - 우승자 리스트(winners)
 */
public record RaceResult(List<LapSnapshotGroup> timeline, List<String> winners) {

    // ===== Constructor =====

    public RaceResult(List<LapSnapshotGroup> timeline,
                      List<String> winners) {
        this.timeline = List.copyOf(timeline);
        this.winners = List.copyOf(winners);
    }
}
