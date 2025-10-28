package racingcar.dto;


/**
 * 자동차의 이름과 해당 시점의 랩 정보를 담는 스냅샷입니다. 불변성을 보장하는 DTO로 사용됩니다.
 *
 * @param carName 자동차 이름
 * @param laps    현재까지 완료한 랩 수
 */
public record LapSnapshot(String carName, int laps) {
}
