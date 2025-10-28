package racingcar.dto;

/**
 * 레이싱 게임 실행에 필요한 입력 값을 담는 요청 객체입니다. 자동차 이름 목록, 시도 횟수, 트랙 길이를 포함합니다.
 */
public record RaceRequest(String[] carNames, int attempts, int trackLength) {

    // ====== Constructor ======

    public RaceRequest {
        validateCarNames(carNames);
        validateAttempts(attempts);
        validateTrackLength(trackLength);

        carNames = carNames.clone();
    }

    // ===== Getter ======

    @Override
    public String[] carNames() {
        return carNames.clone();
    }

    // ===== Validation ======

    private static void validateCarNames(String[] names) {
        if (names == null || names.length == 0) {
            throw new IllegalArgumentException("자동차 이름은 최소 1대 이상이어야 합니다.");
        }
    }

    private static void validateAttempts(int attempts) {
        if (attempts <= 0) {
            throw new IllegalArgumentException("시도 횟수는 1 이상의 정수여야 합니다.");
        }
    }

    private static void validateTrackLength(int trackLength) {
        if (trackLength <= 0) {
            throw new IllegalArgumentException("트랙 길이는 1 이상이어야 합니다.");
        }
    }
}
