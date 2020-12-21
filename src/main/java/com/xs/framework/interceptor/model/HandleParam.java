package com.xs.framework.interceptor.model;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器参数封装实体
 *
 * @author 18871430207@163.com
 */
public class HandleParam {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Object handler;
    private ModelAndView modelAndView;
    private Exception ex;

    public HandleParam(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.request = request;
        this.response = response;
        this.handler = handler;
    }

    public HandleParam(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        this.request = request;
        this.response = response;
        this.handler = handler;
        this.modelAndView = modelAndView;
    }

    public HandleParam(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.request = request;
        this.response = response;
        this.handler = handler;
        this.ex = ex;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public void setModelAndView(ModelAndView modelAndView) {
        this.modelAndView = modelAndView;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

}
