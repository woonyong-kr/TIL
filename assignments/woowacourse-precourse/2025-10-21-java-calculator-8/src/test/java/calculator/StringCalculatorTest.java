package calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;

class StringCalculatorTest {

    @Test
    @DisplayName("빈 문자열 처리: 입력이 길이 0이면 합계 0을 반환.")
    void shouldReturnZeroForEmptyString() {
        // given
        String input = "";
        // when
        int result = StringCalculator.sum(input);
        // then
        assertThat(result).isEqualTo(0);
    }
    @Test
    @DisplayName("공백-only 예외: 입력이 공백 문자만으로 이루어져 있으면 예외를 던짐.")
    void shouldThrowForWhitespaceOnly() {
        // given
        String[] fail = new String[]{" ", "\t", "\n"};

        // when & then
        for (String str : fail) {
            assertThatThrownBy(() -> StringCalculator.sum(str))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
    @Test
    @DisplayName("기본 구분자: 토큰 분리 후 합산.")
    void shouldCalculateSumWithBasicDelimiter() {
        // given
        Map<String, Integer> pass = new LinkedHashMap<>();
        pass.put("1,2",    3);
        pass.put("1:2:3",  6);
        pass.put("1,2:3",  6);
        // when & then
        pass.forEach((str, sum) ->
                assertThat(StringCalculator.sum(str)).isEqualTo(sum)
        );
    }
    @Test
    @DisplayName("커스텀 구분자 형식 검증: 헤더가 존재하면 반드시 문자 1개 + \n 만족.")
    void shouldCalculateSumWithCustomDelimiter() {
        // given
        Map<String, Integer> pass = new LinkedHashMap<>();
        pass.put("//;\\n1;2;3", 6);
        pass.put("//*\\n2*3*4", 9);
        pass.put("//-\\n1-2-3", 6);
        // when & then
        pass.forEach((str, sum) ->
                assertThat(StringCalculator.sum(str)).isEqualTo(sum));
    }
    @Test
    @DisplayName("커스텀 구분자 형식 검증: 잘못된 헤더 형식 감지.")
    void shouldThrowForInvalidCustomHeaderFormat() {
        // given
        String[] fail = new String[]{
                "//;\n1;2;3",           // 실제 개행 (리터럴 아님)
                "//\n1;2;3",            // 구분자 1글자 없음
                "//;;\n1;2;3",          // 구분자 2글자
                "//;;;\\n1;2;3",        // 구분자 3글자 이상
                "/;\\n1;2;3",           // "/" 1개만
                "1;2;3"                 // 헤더 없음
        };
        // when & then
        for (String str : fail) {
            assertThatThrownBy(() -> StringCalculator.sum(str))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
    @Test
    @DisplayName("통합 구분자: 기본 구분자와 커스텀 구분자 혼용.")
    void shouldCalculateSumWithMixedDelimiters() {
        // given
        Map<String, Integer> pass = new LinkedHashMap<>();
        pass.put("//#\\n1,2:3#4", 10);
        pass.put("//*\\n1*2,3:4", 10);
        pass.put("//^\\n1^2,3:4", 10);
        // when & then
        pass.forEach((str, sum) ->
                assertThat(StringCalculator.sum(str)).isEqualTo(sum));
    }
    @Test
    @DisplayName("본문 분리: 정규식 특수문자 처리.")
    void shouldHandleSpecialCharacterDelimiters() {
        // given
        Map<String, Integer> pass = new LinkedHashMap<>();
        pass.put("//]\\n1]2,3:4",       10);
        pass.put("//^\\n1^2,3:4",       10);
        pass.put("//-\\n1-2,3:4",       10);
        pass.put("//[\\n1[2,3:4",       10);
        pass.put("//\\\\n1\\2,3:4",     10);
        // when & then
        pass.forEach((str, sum) ->
                assertThat(StringCalculator.sum(str)).isEqualTo(sum));
    }
    @Test
    @DisplayName("빈/잘못된 토큰 검증: 빈 토큰, 숫자 아닌 문자 감지.")
    void shouldThrowForInvalidTokens() {
        // given
        String[] fail = new String[]{
                "1,,2",         // 빈 토큰
                "1,a,3",        // 문자 포함
                "-1,2,3",       // 음수
                "0,1,2",        // 0 포함
                "1, 2, 3"       // 구분자 내 공백
        };
        // when & then
        for (String str : fail) {
            assertThatThrownBy(() -> StringCalculator.sum(str))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
