package racingcar.domain;

/**
 * 경주 트랙의 길이를 제공하는 인터페이스입니다.
 */
@FunctionalInterface
public interface Track {

    // ===== Behavior =====

    /**
     * 트랙의 길이를 반환합니다.
     *
     * @return 트랙 길이 (양의 정수)
     */
    int length();
}
