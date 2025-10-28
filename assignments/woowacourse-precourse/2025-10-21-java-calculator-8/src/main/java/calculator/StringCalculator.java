package calculator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class StringCalculator {
    // Constants
    private static final Pattern CUSTOM_DELIMITER_HEADER = Pattern.compile("^//(.)\\\\n(.*)$");
    private static final String BASIC_DEFAULT = ",:";

    // Inner types
    private record DelimiterInfo(String delimiters, String body) { }

    // Public methods
    public static int sum(String str) {

        if (str.isEmpty()) return 0;
        if (str.trim().isEmpty()) throw new IllegalArgumentException();

        DelimiterInfo info = parseDelimiter(str);
        String[] tokens = tokenize(info);
        validateTokens(tokens);

        return calculateSum(tokens);
    }

    // Private methods
    private static DelimiterInfo parseDelimiter(String input) {
        if (!input.startsWith("//")) {
            return new DelimiterInfo(BASIC_DEFAULT, input);
        }
        Matcher m = CUSTOM_DELIMITER_HEADER.matcher(input);
        if (!m.matches()) {
            throw new IllegalArgumentException();
        }

        return new DelimiterInfo(BASIC_DEFAULT + escapeForRegex(m.group(1).charAt(0)), m.group(2));
    }

    private static String[] tokenize(DelimiterInfo info) {
        return info.body.split("[" + info.delimiters + "]");
    }

    private static void validateTokens(String[] tokens) {
        for (String t : tokens) {
            if (t.isEmpty())  throw new IllegalArgumentException();
            if (!t.matches("[1-9]\\d*"))  throw new IllegalArgumentException();
        }
    }

    private static int calculateSum(String[] tokens) {
        int sum = 0;
        for (String t : tokens) {
            sum += Integer.parseInt(t);
        }
        return sum;
    }
    private static String escapeForRegex(char c) {
        return switch (c) {
            case '\\' -> "\\\\";
            case ']'  -> "\\]";
            case '['  -> "\\[";
            case '^'  -> "\\^";
            case '-'  -> "\\-";
            default   -> String.valueOf(c);
        };
    }

    // Private constructor
    private StringCalculator() {}
}
