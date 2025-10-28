package racingcar.support;

/**
 * 입력값을 유효성 검증하는 유틸리티 클래스입니다. 문자열 기반 입력을 정수로 변환하고, null/공백/음수 여부를 검사합니다.
 */
public final class InputValidator {

    // ===== Constructor =====

    private InputValidator() {
    }

    // ===== Public Methods =====

    /**
     * 문자열을 파싱하여 양의 정수를 반환합니다. null, 공백, 음수, 숫자 아님 → 예외 발생
     *
     * @param raw          입력 문자열
     * @param errorMessage 유효하지 않을 경우 출력할 예외 메시지
     * @return 양의 정수
     */
    public static int parsePositiveIntOrThrow(String raw, String errorMessage) {
        ensureNotNull(raw, errorMessage);
        String trimmed = trimOrThrowIfEmpty(raw, errorMessage);
        int value = parseIntOrThrow(trimmed, errorMessage);
        ensurePositive(value, errorMessage);
        return value;
    }

    // ===== Validation =====

    private static void ensureNotNull(String raw, String errorMessage) {
        if (raw == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static String trimOrThrowIfEmpty(String raw, String errorMessage) {
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return trimmed;
    }

    private static int parseIntOrThrow(String trimmed, String errorMessage) {
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static void ensurePositive(int value, String errorMessage) {
        if (value <= 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}