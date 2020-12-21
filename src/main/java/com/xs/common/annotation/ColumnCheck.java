package com.xs.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

}
