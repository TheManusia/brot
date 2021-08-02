package xyz.themanusia.brot.helper;

import xyz.themanusia.brot.constant.DBText;

public class Helper {

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static String stringMinimize(String string) {
        if (string.length() > 415)
            return string.substring(0, 415) + "...";
        return string;
    }

    public static String toString(Object object) {
        return String.valueOf(object);
    }

    public static String percentage(double v) {
        return toString((int) (v * 100));
    }

    public static String removePrefix(String command) {
        return command.replace(DBText.DEFAULT_PREFIX, "").trim();
    }
}
