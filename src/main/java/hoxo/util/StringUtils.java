package hoxo.util;

import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isNumber(CharSequence cs) {
        return Pattern.matches("\\d+", cs);
    }

    public static boolean isWord(CharSequence cs) {
        return Pattern.matches("[a-zA-Z]+", cs);
    }
}
