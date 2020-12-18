package com.xs.common.interceptor.service;

import org.springframework.web.method.HandlerMethod;

/**
 * 应用程序接口调用校验接口
 *
 * @author 18871430207@163.com
 */
public interface ApiCheckService {

    /**
     * 应用程序接口校验
     *
     * @param handlerMethod 拦截器方法对象
     * @return 校验结果
     */
    boolean check(HandlerMethod handlerMethod);

}