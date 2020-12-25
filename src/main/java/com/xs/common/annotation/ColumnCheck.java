package com.xs.common.annotation;

import com.xs.common.utils.ClassUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * 自定义字段校验注解
 * 在字段上添加该注解后，会对该字段与数据库字段进行一致性校验
 *
 * @author 18871430207@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnCheck {

    /**
     * 数据库字段名
     */
    String columnName();

    class Utils {

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

}
