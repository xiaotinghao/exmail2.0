package com.xs.common.utils;

import java.util.*;

/**
 * 工具类
 *
 * @author xiaotinghao
 */
public class XsUtils {

    @SuppressWarnings("unchecked")
    public static <T> T cast (Object object) {
        return (T) object;
    }

    /**
     * 获取对象字符串
     *
     * @param object 对象
     * @return 对象字符串
     */
    public static String getStringValue(Object object) {
        if (object != null) {
            if (object instanceof Byte) {
                return object + "";
            } else if (object instanceof Short) {
                return object + "";
            } else if (object instanceof Integer) {
                return object + "";
            } else if (object instanceof Long) {
                return object + "";
            } else if (object instanceof Float) {
                return object + "";
            } else if (object instanceof Double) {
                return object + "";
            } else if (object instanceof Boolean) {
                return object + "";
            } else if (object instanceof Character) {
                return object + "";
            } else if (object instanceof Date) {
                return "'" + DateUtils.formatDateToString((Date) object, DateUtils.DEFAULT_FORMAT) + "'";
            } else if (object instanceof String) {
                return "'" + object + "'";
            } else {
                return "'" + object.toString() + "'";
            }
        } else {
            return "''";
        }
    }

}
