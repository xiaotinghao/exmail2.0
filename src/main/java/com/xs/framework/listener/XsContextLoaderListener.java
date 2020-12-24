package com.xs.framework.listener;

import com.xs.common.annotation.ConfiguredCheck;
import com.xs.common.annotation.TableFieldCheck;
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

    private WebApplicationContext applicationContext;

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        applicationContext = super.initWebApplicationContext(servletContext);

        // 常量类与数据库表进行一致性校验
        TableFieldCheck.Utils.checkTableField(applicationContext);

        // 校验数据常量是否已在数据库中配置
        ConfiguredCheck.Utils.checkConfigured(applicationContext);

        return applicationContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        System.out.println("applicationContext已初始化" + applicationContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
        System.out.println("applicationContext已销毁" + applicationContext);
    }

}