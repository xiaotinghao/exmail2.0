package com.xs.common.utils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
                return "'" + DateUtil.formatDateToString((Date) object, DateUtil.DEFAULT_FORMAT) + "'";
            } else if (object instanceof String) {
                return "'" + object + "'";
            } else {
                return "'" + object.toString() + "'";
            }
        } else {
            return "''";
        }
    }

    public static void main(String[] args) {
        QueueUtils.list.addLast("123");
        QueueUtils.list.addLast("234");
        QueueUtils.list.addLast("345");
    }

}
