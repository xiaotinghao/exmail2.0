package com.xs.common.utils;

import com.xs.common.annotation.ClassFieldAssign;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;

/**
 * 工具类
 *
 * @author 18871430207@163.com
 */
public class XsUtils {

    public static void main(String[] args) {
        String regexStr = "%s%s系统已使用@%s注解，但未在配置文件中发现%s.scanPath";
        String simpleName = ClassFieldAssign.class.getSimpleName();
        String format = String.format(regexStr, LINE_BREAK, TAB, simpleName, simpleName);
        System.out.println(format);

        String[] arr2 = {LINE_BREAK,TAB,simpleName,simpleName};
        String regexStr2 = PropertyUtils.getProperties("file/annotationMsg.txt").getProperty("scanPathMissing");
        String format2 = String.format(regexStr2, arr2);
        System.out.println(format2);

    }

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

    /**
     * 判断数据类型是否为包装类型
     *
     * @param clazz Class对象
     * @return true是，false否
     */
    public static Boolean isPackaged(Class clazz) {
        if (clazz.equals(String.class)) {
            return true;
        }
        if (clazz.equals(Byte.class)) {
            return true;
        }
        if (clazz.equals(Short.class)) {
            return true;
        }
        if (clazz.equals(Integer.class)) {
            return true;
        }
        if (clazz.equals(Float.class)) {
            return true;
        }
        if (clazz.equals(Double.class)) {
            return true;
        }
        if (clazz.equals(Character.class)) {
            return true;
        }
        if (clazz.equals(Long.class)) {
            return true;
        }
        if (clazz.equals(Boolean.class)) {
            return true;
        }
        return false;
    }

    /**
     * 判断数据类型是否为基本类型或包装类型
     *
     * @param clazz Class对象
     * @return true是，false否
     */
    public static Boolean isPrimitiveOrPackaged(Class clazz) {
        // 是基本类型
        if (clazz.isPrimitive()) {
            return true;
        }
        return isPackaged(clazz);
    }

    /**
     * 包装类型转换为基本类型
     *
     * @param clazz 包装类型
     * @return 基本类型
     */
    public static Class getPrimitiveClass(Class clazz) {
        if (clazz.equals(Byte.class)) {
            return Byte.TYPE;
        } else if (clazz.equals(Short.class)) {
            return Short.TYPE;
        } else if (clazz.equals(Integer.class)) {
            return Integer.TYPE;
        } else if (clazz.equals(Float.class)) {
            return Float.TYPE;
        } else if (clazz.equals(Double.class)) {
            return Double.TYPE;
        } else if (clazz.equals(Character.class)) {
            return Character.TYPE;
        } else if (clazz.equals(Long.class)) {
            return Long.TYPE;
        } else if (clazz.equals(Boolean.class)) {
            return Boolean.TYPE;
        } else {
            return clazz;
        }
    }

    /**
     * 基本类型转换为包装类型
     *
     * @param clazz 基本类型
     * @return 包装类型
     */
    public static Class getPackagedClass(Class clazz) {
        if (clazz.equals(byte.class)) {
            return Byte.class;
        } else if (clazz.equals(short.class)) {
            return Short.class;
        } else if (clazz.equals(int.class)) {
            return Integer.class;
        } else if (clazz.equals(float.class)) {
            return Float.class;
        } else if (clazz.equals(double.class)) {
            return Double.class;
        } else if (clazz.equals(char.class)) {
            return Character.class;
        } else if (clazz.equals(long.class)) {
            return Long.class;
        } else if (clazz.equals(boolean.class)) {
            return Boolean.class;
        } else {
            return clazz;
        }
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
