package com.xs.common.aop;

import com.xs.common.annotation.InterfaceLog;
import com.xs.module.exmail.log.service.InterfaceLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义日志切面
 *
 * @author 18871430207@163.com
 * @apiNote 使用Order改变切面顺序，数值越小优先级越高
 */
@Component
@Aspect
@Order(1)
public class InterfaceLogAspect {

    @Autowired
    InterfaceLogService interfaceLogService;

    @Pointcut("execution (* com.xs.module.**.api.*.*(..)) || execution (* com.xs.module.**.cgi.*.*(..))")
    public void pointcut() {

    }

    @Before("InterfaceLogAspect.pointcut()")
    public void beforeAdvice(JoinPoint point) {
        String className = point.getTarget().getClass().getName();
        Signature signature = point.getSignature();
        String methodName = signature.getName();
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 通过这获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = point.getTarget().getClass().getMethod(methodName, argTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取方法上的注解
        if (method != null) {
            InterfaceLog interfaceLog = method.getAnnotation(InterfaceLog.class);
            if (interfaceLog != null) {
                // 方法执行前
                System.out.println("before:" + methodName);
                interfaceLogService.saveLog(className, methodName, args, parameterNames);
            }
        }
    }

}
