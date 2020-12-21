package com.xs.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义数据表校验注解
 * 在常量类上添加该注解后，会对该常量类与数据库表名进行一致性校验
 *
 * @author 18871430207@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableCheck {

    /**
     * 数据库表名
     */
    String tableName();

}
