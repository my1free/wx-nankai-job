package com.nankai.wx.job.util;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/15
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class NumberUtil {
    public static boolean isNullOrZero(Number value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer) {
            return (Integer) value == 0;
        }
        if (value instanceof Long) {
            return (Long) value == 0;
        }
        return false;
    }

    public static boolean isNullOrNonePositive(Number value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer) {
            return (Integer) value <= 0;
        }
        if (value instanceof Long) {
            return (Long) value <= 0;
        }
        return false;
    }
}
