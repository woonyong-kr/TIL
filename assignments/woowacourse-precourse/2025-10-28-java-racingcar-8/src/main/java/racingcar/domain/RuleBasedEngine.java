package racingcar.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Objects;

/**
 * 주어진 규칙(Rule)에 따라 난수 기반으로 이동 가능 여부를 판단하는 엔진입니다.
 */
public class RuleBasedEngine implements Engine {

    // ===== Fields =====

    private final Rule rule;

    // ===== Constructor =====

    public RuleBasedEngine(Rule providedRule) {
        this.rule = Objects.requireNonNull(providedRule, "Rule은 null일 수 없습니다.");
    }

    // ===== Behavior =====

    /**
     * 0~9 사이의 난수를 생성하여 rule이 만족되는지 여부를 반환합니다.
     *
     * @return rule을 만족하면 true, 아니면 false
     */
    @Override
    public boolean isMovable() {
        int randomNumber = Randoms.pickNumberInRange(0, 9);
        return rule.isSatisfiedBy(randomNumber);
    }
}
