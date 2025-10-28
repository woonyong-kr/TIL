package racingcar.domain;

/**
 * 주어진 난수가 임계값 이상인지 여부를 판단하는 규칙입니다.
 */
public class ThresholdRule implements Rule {

    // ===== Fields =====

    private final int thresholdInclusive;

    // ===== Constructor =====

    public ThresholdRule(int thresholdInclusive) {
        this.thresholdInclusive = thresholdInclusive;
    }

    // ===== Behavior =====

    @Override
    public boolean isSatisfiedBy(int randomNumber) {
        return randomNumber >= thresholdInclusive;
    }
}