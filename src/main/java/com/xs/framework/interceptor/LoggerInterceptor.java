package com.xs.framework.interceptor;

import com.xs.framework.interceptor.model.HandleParam;
import com.xs.module.log.service.InterfaceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.xs.common.constants.WebConstants.HANDLE_START_TIME;

/**
 * 日志拦截器
 *
 * @author 18871430207@163.com
 */
public class LoggerInterceptor implements HandlerInterceptor {

    /**
     * 封装拦截器参数
     */
    private HandleParam handleParam;

    @Autowired
    InterfaceLogService interfaceLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 封装拦截器参数
        handleParam = new HandleParam(request, response, handler);
        // 保存请求开始时间
        request.setAttribute(HANDLE_START_TIME, new Date());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 封装拦截器参数
        handleParam = new HandleParam(request, response, handler, ex);
        // 保存接口请求日志
        interfaceLogService.saveCallLog(handleParam);
    }

}