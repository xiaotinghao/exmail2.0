package com.xs.framework.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 自定义 HttpSessionListener
 * 在需要显示在线人数的地方通过session.getAttribute("onLineCount") 即可获取在线人数值
 *
 * @author 18871430207@163.com
 */
@WebListener
public class XsHttpSessionListener implements HttpSessionListener {

    public int count;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        count++;
        httpSessionEvent.getSession().getServletContext().setAttribute("onLineCount", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        count--;
        httpSessionEvent.getSession().getServletContext().setAttribute("onLineCount", count);
    }

}