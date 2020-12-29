package com.xs.framework.listener;

import com.xs.common.annotation.AnnotationBase;
import com.xs.common.annotation.Assign;
import com.xs.common.annotation.Table;
import com.xs.common.utils.spring.SpringTool;
import com.xs.module.qwer1234.dao.ModuleBaseDao;
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

        ModuleBaseDao baseDao = SpringTool.getBean(ModuleBaseDao.class);

        // 初始化注解使用的数据服务对象
        AnnotationBase.init(baseDao);
        // 对象字段赋值
        Assign.Utils.assign();
        // 对象字段赋值
        Table.Utils.assign();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

}