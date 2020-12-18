package com.xs.common.interceptor;

import com.xs.common.interceptor.service.CgiCheckService;
import com.xs.common.interceptor.service.InterfaceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共网关接口校验拦截器
 *
 * @author 18871430207@163.com
 */
public class CgiCheckInterceptor implements HandlerInterceptor {

    @Autowired
    InterfaceCheckService interfaceCheckService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return interfaceCheckService.check((HandlerMethod) handler);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }

}
