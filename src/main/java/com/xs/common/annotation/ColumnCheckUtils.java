package com.xs.common.annotation;

import com.xs.common.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * 字段校验注解工具类
 *
 * @author 18871430207@163.com
 */
public class ColumnCheckUtils {

    /**
     * 加载ColumnCheck注解对应的字段值
     *
     * @param clazz Class类
     */
    public static void initFieldValue(Class clazz) {
        Object obj = ClassUtils.newInstance(clazz);
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            try {
                Object value = field.get(obj);
                if (value == null) {
                    field.set(obj, field.getAnnotation(ColumnCheck.class).columnName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
