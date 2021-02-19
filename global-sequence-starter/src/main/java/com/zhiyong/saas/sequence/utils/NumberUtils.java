package com.zhiyong.saas.sequence.utils;

public class NumberUtils {

    /**
     *
     * @param str
     * @return
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
