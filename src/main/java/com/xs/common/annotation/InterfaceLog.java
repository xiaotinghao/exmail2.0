package com.xs.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义接口日志生成注解
 * 在接口方法上添加该注解后，每次调用接口会在t_input_interface_call_log表中新增日志
 *
 * @author 18871430207@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InterfaceLog {

}
