package com.xs.common.servlet;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义 DispatcherServlet 来分派 XsHttpServletRequestWrapper
 *
 * @author 18871430207@163.com
 */
public class XsDispatcherServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new XsHttpServletRequestWrapper(request), response);
    }

}