package racingcar.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import racingcar.domain.Car;
import racingcar.domain.Track;
import racingcar.dto.LapSnapshotGroup;
import racingcar.dto.assembler.LapSnapshotAssembler;


/**
 * 레이싱 게임의 실행과 결과 저장을 담당하는 클래스
 */
public class RacingGame {

    // ===== Fields =====

    private final Track track;
    private final int attempts;
    private final List<RacingLapState> lapStates;
    private final List<LapSnapshotGroup> historyByLap = new ArrayList<>();

    // ===== Constructor =====

    public RacingGame(List<Car> providedParticipants, Track providedTrack, int providedAttempts) {
        validateAttemptsIsPositive(providedAttempts);
        this.track = Objects.requireNonNull(providedTrack, "Track은 null일 수 없습니다.");
        this.lapStates = createLapStates(providedParticipants);
        this.attempts = providedAttempts;
    }

    // ===== Public Methods =====

    public void ready() {
        for (RacingLapState lapState : lapStates) {
            lapState.initialize();
        }
        historyByLap.clear();
    }

    public void play() {
        for (int i = 0; i <= attempts - 1; i++) {
            for (RacingLapState lapState : lapStates) {
                if (lapState.moveIfPossible()) {
                    lapState.syncFromCar(track.length());
                }
            }
            captureLapSnapshot();
        }
    }

    public int getLapOf(String carName) {
        for (RacingLapState lapState : lapStates) {
            if (lapState.getName().equals(carName)) {
                return lapState.getLap();
            }
        }
        throw new IllegalArgumentException("해당 이름의 자동차가 없습니다: " + carName);
    }

    public int getDistanceInLapOf(String carName) {
        for (RacingLapState lapState : lapStates) {
            if (lapState.getName().equals(carName)) {
                return lapState.getDistanceInLap();
            }
        }
        throw new IllegalArgumentException("해당 이름의 자동차가 없습니다: " + carName);
    }

    public List<LapSnapshotGroup> getHistory() {
        return new ArrayList<>(historyByLap);
    }

    public List<String> getWinners() {
        return new ResultEvaluator().extractWinners(lapStates, track.length());
    }

    // ===== Private Methods =====

    private List<RacingLapState> createLapStates(List<Car> participants) {
        validateNoDuplicateNames(participants);
        List<RacingLapState> list = new ArrayList<>();
        for (Car car : participants) {
            list.add(new RacingLapState(car));
        }
        return list;
    }

    private void captureLapSnapshot() {
        historyByLap.add(LapSnapshotAssembler.assemble(lapStates));
    }

    private void validateNoDuplicateNames(List<Car> participants) {
        int totalCount = participants.size();
        long uniqueNameCount = participants.stream()
                .map(Car::getName)
                .distinct()
                .count();

        if (totalCount > uniqueNameCount) {
            throw new IllegalArgumentException("중복된 자동차 이름이 존재합니다. 각 자동차 이름은 고유해야 합니다.");
        }
    }

    private void validateAttemptsIsPositive(int providedAttempts) {
        if (providedAttempts <= 0) {
            throw new IllegalArgumentException("시도 횟수는 0 이상 정수어야 합니다.");
        }
    }
}