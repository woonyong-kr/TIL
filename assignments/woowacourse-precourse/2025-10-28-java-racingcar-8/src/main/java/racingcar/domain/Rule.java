package racingcar.domain;

/**
 * 랜덤 숫자가 특정 조건(예: 임계값 이상)을 만족하는지 판단하는 규칙을 표현합니다.
 */
@FunctionalInterface
public interface Rule {

    // ===== Behavior =====

    /**
     * 주어진 숫자가 규칙을 만족하는지 여부를 반환합니다.
     *
     * @param randomNumber 판단 대상 숫자
     * @return 조건을 만족하면 true, 아니면 false
     */
    boolean isSatisfiedBy(int randomNumber);
}
