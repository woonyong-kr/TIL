package racingcar.factory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import racingcar.domain.Car;
import racingcar.domain.Engine;

public final class CarFactory {

    // ===== Static Factory Method =====

    /**
     * 주어진 이름 목록과 엔진으로 자동차 리스트를 생성합니다.
     *
     * @param names  자동차 이름 목록
     * @param engine 공유할 엔진 인스턴스
     * @return Car 객체 리스트
     */
    public static List<Car> from(String[] names, Engine engine) {
        return Arrays.stream(names)
                .map(name -> new Car(name, engine))
                .collect(Collectors.toList());
    }

    // ===== Constructor =====

    private CarFactory() {
    }
}