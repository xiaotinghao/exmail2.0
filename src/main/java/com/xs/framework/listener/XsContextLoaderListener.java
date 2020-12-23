package com.xs.framework.listener;

import com.xs.common.dao.BaseDao;
import com.xs.common.exception.XsException;
import com.xs.common.utils.*;
import com.xs.common.annotation.ColumnCheck;
import com.xs.common.annotation.TableCheck;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;
import static com.xs.common.constants.dynamic.ConstantsBase.LOG_MSG_PREFIX;

/**
 * 自定义 ContextLoaderListener
 *
 * @author 18871430207@163.com
 */
public class XsContextLoaderListener extends ContextLoaderListener {

    private WebApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
    }

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        applicationContext = super.initWebApplicationContext(servletContext);
        // 校验数据常量是否已在数据库中配置
        if (!checkConfigured()) {
            throw new XsException("数据常量未在数据库中配置");
        }
        // 校验数据库字段常量是否与数据库字段一致
        if (!checkConsistency()) {
            throw new XsException("数据库字段常量与数据库字段不一致");
        }
        return applicationContext;
    }

    @Override
    protected ApplicationContext loadParentContext(ServletContext servletContext) {
        return super.loadParentContext(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

    private boolean checkConfigured() throws RuntimeException {
        BaseDao baseDao = applicationContext.getBean(BaseDao.class);
        List<String> errMsgList = new LinkedList<>();
        String locationPattern = "classpath*:com/xs/**/constants/dynamic/*.class";
        List<Class<?>> classes = ClassUtils.getClasses(locationPattern);
        if (classes != null && !classes.isEmpty()) {
            for (Class<?> clazz : classes) {
                if (clazz != null) {
                    TableCheck tableCheckAnnotation = clazz.getAnnotation(TableCheck.class);
                    if (tableCheckAnnotation != null) {
                        String tableName = tableCheckAnnotation.tableName();
                        // 查询数据表是否存在
                        String tableCheckResult = baseDao.checkTable(tableName);
                        if (StringUtils.isEmpty(tableCheckResult)) {
                            String errMsg = "数据库表`" + tableName + "`不存在，系统自动生成中...";
                            errMsgList.add(errMsg);
                            try {
                                baseDao.createTable(tableName);
                                errMsg = "数据库表`" + tableName + "`已自动生成";
                                errMsgList.add(errMsg);
                            } catch (Exception e) {
                                errMsg = "数据库表`" + tableName + "`自动生成失败，请手动创建";
                                errMsgList.add(errMsg);
                            }
                        }
                        Object obj = ClassUtils.newInstance(clazz);
                        List<String> keys = baseDao.queryKeys(tableName);
                        Field[] fields = obj.getClass().getFields();
                        for (Field field : fields) {
                            String fieldName = field.getName();
                            if (!keys.contains(fieldName)) {
                                // 常量未在`t_constants_*`表中配置
                                String errMsg = clazz.getName() + "." + fieldName + "常量未在数据库表`" + tableName + "`中配置";
                                errMsgList.add(errMsg);
                            }
                        }
                    }
                }
            }
        }
        if (!errMsgList.isEmpty()) {
            throw new XsException(CollectionUtils.toString(errMsgList, LINE_BREAK + TAB));
        }
        return true;
    }

    private boolean checkConsistency() throws RuntimeException {
        BaseDao baseDao = applicationContext.getBean(BaseDao.class);
        List<String> errMsgList = new LinkedList<>();
        String locationPattern = "classpath*:com/xs/module/constants/*.class";
        List<Class<?>> classes = ClassUtils.getClasses(locationPattern);
        if (classes != null && !classes.isEmpty()) {
            for (Class<?> clazz : classes) {
                if (clazz != null) {
                    TableCheck tableCheckAnnotation = clazz.getAnnotation(TableCheck.class);
                    if (tableCheckAnnotation != null) {
                        String tableName = tableCheckAnnotation.tableName();
                        // 查询数据表是否存在
                        String tableCheckResult = baseDao.checkTable(tableName);
                        if (StringUtils.isEmpty(tableCheckResult)) {
                            String errMsg = LOG_MSG_PREFIX + "数据库表`" + tableName + "`不存在";
                            errMsgList.add(errMsg);
                        }
                        Field[] fields = clazz.getFields();
                        for (Field field : fields) {
                            String fieldName = field.getName();
                            ColumnCheck columnCheckAnnotation = field.getAnnotation(ColumnCheck.class);
                            if (columnCheckAnnotation != null) {
                                String columnName = columnCheckAnnotation.columnName();
                                // 查询数据表的字段是否存在
                                String columnCheckResult = baseDao.checkColumn(tableName, columnName);
                                if (StringUtils.isEmpty(columnCheckResult)) {
                                    String errMsg = LOG_MSG_PREFIX + "数据库表`" + tableName + "`字段`" + columnName + "`不存在";
                                    errMsgList.add(errMsg);
                                }
                            } else {
                                String errMsg = LOG_MSG_PREFIX + "[" + clazz.getName() + "]类中的[" + fieldName + "]字段无@" + ColumnCheck.class.getSimpleName() + "注解";
                                errMsgList.add(errMsg);
                            }
                        }
                    }
                }
            }
        }
        if (!errMsgList.isEmpty()) {
            throw new XsException(CollectionUtils.toString(errMsgList, LINE_BREAK + TAB));
        }
        return true;
    }

}