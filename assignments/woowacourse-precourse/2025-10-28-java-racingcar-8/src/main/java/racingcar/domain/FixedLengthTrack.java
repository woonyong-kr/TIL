package racingcar.domain;


/**
 * 고정된 길이를 가진 트랙입니다. 생성 시 길이를 지정하며, 이후 변경되지 않습니다.
 */
public record FixedLengthTrack(int length) implements Track {

    // ===== Validation =====

    public FixedLengthTrack {
        if (length <= 0) {
            throw new IllegalArgumentException("트랙 길이는 1 이상이어야 합니다.");
        }
    }

    // ===== Behavior =====

    @Override
    public int length() {
        return length;
    }
}
