package com.xs.framework.listener;

import com.xs.common.annotation.ClassFieldAssign;
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

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        // 返回WebApplicationContext对象
        return super.initWebApplicationContext(servletContext);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        // 常量类与数据库表进行一致性校验
//        TableFieldCheck.Utils.checkTableField();
        // 校验数据常量是否已在数据库中配置
//        ConfiguredCheck.Utils.checkConfigured();
        // 对象字段赋值
        ClassFieldAssign.Utils.assign();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

}