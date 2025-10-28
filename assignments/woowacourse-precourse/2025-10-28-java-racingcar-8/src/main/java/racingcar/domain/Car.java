package racingcar.domain;

import java.util.Objects;

/**
 * 자동차는 이름, 이동 거리, 엔진을 상태로 가지며, 엔진이 전진 가능하다고 판단되면 이동합니다.
 */
public class Car {

    // ===== Fields =====

    private final String name;
    private final Engine engine;
    private int distance;

    // ===== Constructor =====

    public Car(String candidateName, Engine providedEngine) {
        validateName(candidateName);
        this.name = candidateName;
        this.engine = Objects.requireNonNull(providedEngine, "Engine은 null일 수 없습니다.");
        this.distance = 0;
    }

    // ===== Public Methods =====

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }


    // 전진하면 true, 아니면 false를 반환
    public boolean move() {
        if (engine.isMovable()) {
            distance++;
            return true;
        }
        return false;
    }

    // ===== Validation =====

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty() || name.length() > 5) {
            throw new IllegalArgumentException("자동차 이름은 1~5자 이내여야 합니다.");
        }
    }
}