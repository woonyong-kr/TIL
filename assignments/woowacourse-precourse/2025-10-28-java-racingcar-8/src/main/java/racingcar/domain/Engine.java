package racingcar.domain;

/**
 * 엔진은 이동 가능 여부를 판단하는 전략 인터페이스입니다. 구현체는 난수 기반, 규칙 기반 등 다양한 방식으로 전진 조건을 정의할 수 있습니다.
 */
@FunctionalInterface
public interface Engine {

    // ===== Behavior =====

    /**
     * 전진 가능한 조건인지 확인합니다.
     *
     * @return true면 전진 가능, false면 정지
     */
    boolean isMovable();
}
