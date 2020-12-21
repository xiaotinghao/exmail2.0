package com.xs.framework.service;

import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 应用程序接口调用校验接口
 *
 * @author 18871430207@163.com
 */
public interface ApiCheckService {

    /**
     * 应用程序接口校验
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  拦截器对象
     * @return 校验结果
     */
    boolean check(HttpServletRequest request, HttpServletResponse response, Object handler);

}