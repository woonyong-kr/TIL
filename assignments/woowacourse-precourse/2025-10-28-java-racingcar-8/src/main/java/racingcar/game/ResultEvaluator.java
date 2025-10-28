package racingcar.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 경기 결과에서 우승자를 계산하는 책임을 담당합니다.
 */
public class ResultEvaluator {

    // ===== Public Methods =====

    /**
     * 주어진 기록과 트랙 길이를 기반으로 우승자 이름 목록을 반환합니다.
     *
     * @param records     자동차별 기록 리스트
     * @param trackLength 트랙 길이
     * @return 공동 우승자 포함 우승자 이름 목록
     */
    public List<String> extractWinners(List<RacingLapState> records, int trackLength) {
        if (records.isEmpty()) {
            return Collections.emptyList();
        }

        records.sort(Comparator.comparingInt((RacingLapState r) ->
                r.getLap() * trackLength + r.getDistanceInLap()
        ).reversed());

        int topScore = calculateScore(records.getFirst(), trackLength);
        List<String> winners = new ArrayList<>();

        for (RacingLapState record : records) {
            if (calculateScore(record, trackLength) != topScore) {
                break;
            }
            winners.add(record.getName());
        }
        return winners;
    }

    // ===== Private Methods =====

    private int calculateScore(RacingLapState record, int trackLength) {
        return record.getLap() * trackLength + record.getDistanceInLap();
    }
}
