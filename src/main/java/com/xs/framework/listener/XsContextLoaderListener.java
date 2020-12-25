package com.xs.framework.listener;

import com.xs.common.annotation.ClassFieldAssign;
import com.xs.common.annotation.MatchTable;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * 自定义 ContextLoaderListener
 *
 * @author 18871430207@163.com
 */
public class XsContextLoaderListener extends ContextLoaderListener {

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        // 返回WebApplicationContext对象
        return super.initWebApplicationContext(servletContext);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        // 对象字段赋值
        ClassFieldAssign.Utils.assign();
        // 对象字段赋值
        MatchTable.Utils.assign();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

}