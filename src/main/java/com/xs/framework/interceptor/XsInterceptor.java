package com.xs.framework.interceptor;

import com.xs.common.constants.ConstantsBase;
import com.xs.common.service.BaseService;
import com.xs.framework.interceptor.model.HandleParam;
import com.xs.module.corp.service.CorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 自定义拦截器
 *
 * @author 18871430207@163.com
 */
public class XsInterceptor implements HandlerInterceptor {

    /**
     * 封装拦截器参数
     */
    private HandleParam handleParam;

    @Autowired
    BaseService baseService;
    @Autowired
    CorpService corpService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 封装拦截器参数
        handleParam = new HandleParam(request, response, handler);
        // 刷新常量配置
        baseService.refreshConstants();
        // 保存请求开始时间
        request.setAttribute(ConstantsBase.HANDLE_START_TIME, new Date());
        // 保存企业id和客户端ip的匹配关系
        corpService.saveCorpIpRelation(handleParam);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 封装拦截器参数
        handleParam = new HandleParam(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 封装拦截器参数
        handleParam = new HandleParam(request, response, handler, ex);
    }

}