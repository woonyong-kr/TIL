package racingcar.controller;

import java.util.List;
import racingcar.dto.RaceResult;
import racingcar.domain.Car;
import racingcar.domain.FixedLengthTrack;
import racingcar.domain.Track;
import racingcar.dto.assembler.RaceResultAssembler;
import racingcar.game.RacingGame;

/**
 * 레이싱 게임의 전체 흐름을 조율하는 클래스입니다. 경주 준비, 실행, 결과 생성까지 담당합니다.
 */
public class RaceDirector {

    // ===== Public Methods =====

    /**
     * 자동차 목록과 시도 횟수, 트랙 길이를 받아 전체 레이싱을 실행하고 결과를 반환합니다.
     *
     * @param cars        자동차 객체 리스트
     * @param attempts    라운드 시도 횟수
     * @param trackLength 트랙의 고정 길이
     * @return 레이싱 결과 객체
     */
    public RaceResult runRace(List<Car> cars, int attempts, int trackLength) {
        Track track = new FixedLengthTrack(trackLength);
        RacingGame game = new RacingGame(cars, track, attempts);

        game.ready();
        game.play();

        return RaceResultAssembler.assemble(game.getHistory(), game.getWinners());
    }
}
