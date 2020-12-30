package com.xs.common.utils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 工具类
 *
 * @author 18871430207@163.com
 */
public class XsUtils {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }

    /**
     * 将字符串转换为其他包装类型
     *
     * @param clazz 包装类型的Class类
     * @param value 字符串
     * @return 包装类型对象
     */
    public static Object cast(Class clazz, String value) {
        if (clazz == null) {
            return null;
        }
        if (value == null || value.length() == 0) {
            return null;
        }
        if (clazz.equals(Character.class)) {
            return value.toCharArray()[0];
        }
        if (clazz.equals(Date.class)) {
            return DateUtils.parseDate(value);
        }
        try {
            Constructor constructor = clazz.getConstructor(String.class);
            return constructor.newInstance(value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Field field, Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
