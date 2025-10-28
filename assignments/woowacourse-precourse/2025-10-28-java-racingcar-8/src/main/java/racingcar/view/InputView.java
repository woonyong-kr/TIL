package racingcar.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.Objects;
import racingcar.dto.RaceRequest;
import racingcar.support.InputValidator;

public class InputView {

    // ===== Constants =====

    private static final int DEFAULT_TRACK_LENGTH = 1;

    // ===== Public Methods =====

    /**
     * 사용자로부터 경주 정보를 입력받아 RaceRequest 객체를 생성합니다.
     *
     * @return RaceRequest 객체
     */
    public RaceRequest askRaceRequest() {
        String[] carNames = readCarNames();
        int attempts = readAttempts();
        return new RaceRequest(carNames, attempts, DEFAULT_TRACK_LENGTH);
    }

    // ===== Private Methods =====

    private String[] readCarNames() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String rawNames = Objects.requireNonNull(Console.readLine(), "자동차 이름 입력값은 null일 수 없습니다.");

        String[] carNames = Arrays.stream(rawNames.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        if (carNames.length == 0) {
            throw new IllegalArgumentException("자동차 이름은 최소 1개 이상이어야 합니다.");
        }

        return carNames;
    }

    private int readAttempts() {
        System.out.println("시도할 횟수는 몇 회인가요?");
        String rawAttempts = Objects.requireNonNull(Console.readLine(), "시도 횟수 입력값은 null일 수 없습니다.");

        return InputValidator.parsePositiveIntOrThrow(
                rawAttempts,
                "시도 횟수는 1 이상의 정수여야 합니다."
        );
    }
}
