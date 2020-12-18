package com.xs.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * Java反射工具类
 *
 * @author 18871430207@163.com
 */
public class ReflectUtils {

    /**
     * java反射bean的get方法
     *
     * @param objectClass 对象类型
     * @param fieldName   属性名称
     * @return get方法
     */
    @SuppressWarnings("unchecked")
    public static Method getGetMethod(Class objectClass, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * java反射bean的set方法
     *
     * @param objectClass 对象类型
     * @param fieldName   属性名称
     * @return set方法
     */
    @SuppressWarnings("unchecked")
    public static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuilder sb = new StringBuilder();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            return objectClass.getMethod(sb.toString(), parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行set方法
     *
     * @param o         执行对象
     * @param fieldName 属性
     * @param value     值
     */
    public static void invokeSet(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);
        try {
            method.invoke(o, new Object[]{value});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行get方法
     *
     * @param o         执行对象
     * @param fieldName 属性
     */
    public static Object invokeGet(Object o, String fieldName) {
        Method method = getGetMethod(o.getClass(), fieldName);
        try {
            return method.invoke(o, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取方法调用者的类名、方法名、代码行数
     *
     * @return Array 方法调用者的类名、方法名、代码行数
     * className array[0]
     * methodName array[1]
     * lineNumber array[2]
     */
    public static String[] getClassMethodLine() {
        String[] result = new String[3];
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement log = stackTrace[1];
        boolean flag = false;
        for (int i = 1; i < stackTrace.length; i++) {
            StackTraceElement e = stackTrace[i];
            if (!e.getClassName().equals(log.getClassName())) {
                result[0] = e.getClassName();
                result[1] = e.getMethodName();
                result[2] = String.valueOf(e.getLineNumber());
                flag = true;
                break;
            }
        }
        if (!flag) {
            result[0] = log.getClassName();
            result[1] = log.getMethodName();
            result[2] = String.valueOf(log.getLineNumber());
        }
        return result;
    }

    public <T> Class<T> getClazz() {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }

}
