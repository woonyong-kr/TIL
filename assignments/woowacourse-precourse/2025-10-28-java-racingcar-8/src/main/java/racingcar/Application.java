package racingcar;

import java.util.List;
import racingcar.controller.RaceDirector;
import racingcar.dto.RaceRequest;
import racingcar.dto.RaceResult;
import racingcar.domain.Car;
import racingcar.domain.Engine;
import racingcar.domain.Rule;
import racingcar.domain.RuleBasedEngine;
import racingcar.domain.ThresholdRule;
import racingcar.view.InputView;
import racingcar.view.OutputView;
import racingcar.factory.CarFactory;

public class Application {

    // ===== Constants =====

    private static final int MOVING_FORWARD_RULE = 4;

    // ===== Entry Point =====

    public static void main(String[] args) {

        // 입력 처리
        InputView inputView = new InputView();
        RaceRequest raceRequest = inputView.askRaceRequest();

        // 레이스 환경 구성
        Rule raceRule = new ThresholdRule(MOVING_FORWARD_RULE);
        Engine raceEngine = new RuleBasedEngine(raceRule);

        List<Car> cars = CarFactory.from(raceRequest.carNames(), raceEngine);

        // 게임 실행
        RaceDirector raceDirector = new RaceDirector();
        RaceResult raceResult = raceDirector.runRace(
                cars,
                raceRequest.attempts(),
                raceRequest.trackLength()
        );

        // 출력
        OutputView outputView = new OutputView();
        outputView.print(raceResult);
    }
}
