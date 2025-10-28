package racingcar.game;

import racingcar.domain.Car;

/**
 * 자동차의 현재 주행 상태를 추적하기 위한 클래스입니다. 기준 거리(baseline)로부터의 진행 상황을 바탕으로 랩과 랩 내 거리 계산 외부에서는 랩 수, 랩 내 거리, 자동차 이름만 읽을 수 있습니다.
 */
public class RacingLapState {

    // ===== Fields =====

    private final Car car;
    private int baselineDistance;
    private int lap;
    private int distanceInLap;

    // ===== Constructor =====

    public RacingLapState(Car providedCar) {
        this.car = providedCar;
        this.baselineDistance = providedCar.getDistance();
        this.lap = 0;
        this.distanceInLap = 0;
    }

    // ===== Behavior =====

    /**
     * 기준 거리와 상태를 초기화합니다.
     */
    public void initialize() {
        this.baselineDistance = car.getDistance();
        this.lap = 0;
        this.distanceInLap = 0;
    }

    /**
     * 자동차의 현재 거리에서 기준 거리를 빼서 랩과 랩 내 거리를 계산합니다.
     *
     * @param trackLength 트랙 길이
     */
    public void syncFromCar(int trackLength) {
        int raceProgress = car.getDistance() - baselineDistance;
        if (raceProgress < 0) {
            raceProgress = 0;
        }

        this.lap = raceProgress / trackLength;
        this.distanceInLap = raceProgress % trackLength;
    }

    // ===== Getter =====

    public String getName() {
        return car.getName();
    }

    public int getLap() {
        return lap;
    }

    public int getDistanceInLap() {
        return distanceInLap;
    }

    public boolean moveIfPossible() {
        return car.move();
    }
}
