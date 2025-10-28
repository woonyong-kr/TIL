package racingcar.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.support.InputValidator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {
    private static final String MESSAGE = "시도 횟수는 1 이상의 정수여야 합니다.";

    @ParameterizedTest
    @ValueSource(strings = {"1", "3", "5", "7"})
    @DisplayName("양의 정수 문자열은 정수로 변환된다")
    void parsePositiveInt_whenValid_returnsInt(String validInput) {
        int result = InputValidator.parsePositiveIntOrThrow(validInput, MESSAGE);
        assertThat(result).isEqualTo(Integer.parseInt(validInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "00", "000"})
    @DisplayName("입력값이 0인 경우 예외가 발생한다")
    void parsePositiveInt_whenZero_throws(String input) {
        assertInvalid(input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-3", "-42"})
    @DisplayName("입력값이 음수인 경우 예외가 발생한다")
    void parsePositiveInt_whenNegative_throws(String input) {
        assertInvalid(input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "12a", "one", "1 2", "3.14"})
    @DisplayName("정수가 아닌 문자열 입력 시 예외가 발생한다")
    void parsePositiveInt_whenNotNumber_throws(String input) {
        assertInvalid(input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    @DisplayName("빈 문자열 또는 공백만 있는 입력 시 예외 발생다")
    void parsePositiveInt_whenBlank_throws(String input) {
        assertInvalid(input);
    }

    // ===== 유틸 =====

    private void assertInvalid(String input) {
        assertThatThrownBy(() ->
                InputValidator.parsePositiveIntOrThrow(input, MESSAGE)
        )
                .as("입력값 '%s' 은(는) 유효하지 않아야 한다", input)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MESSAGE);
    }
}
