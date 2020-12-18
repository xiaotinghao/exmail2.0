package com.xs.common.interceptor.service;

import org.springframework.web.method.HandlerMethod;

/**
 * 公共网关接口调用校验接口
 *
 * @author 18871430207@163.com
 */
public interface CgiCheckService {

    /**
     * 公共网关接口校验
     *
     * @param handlerMethod 拦截器方法对象
     * @return 校验结果
     */
    boolean check(HandlerMethod handlerMethod);

}