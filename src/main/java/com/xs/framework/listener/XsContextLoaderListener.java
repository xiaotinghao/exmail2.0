package com.xs.framework.listener;

import com.xs.common.constants.ConstantsConfig;
import com.xs.common.dao.BaseDao;
import com.xs.common.exception.XsException;
import com.xs.common.utils.ClassUtils;
import com.xs.common.utils.CollectionUtils;
import com.xs.common.annotation.ColumnCheck;
import com.xs.common.annotation.TableCheck;
import com.xs.common.utils.StringUtils;
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
        // 校验数据库字段常量是否与数据库字段一致
        if (!check()) {
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

    private boolean check() throws RuntimeException {
        BaseDao baseDao = applicationContext.getBean(BaseDao.class);
        List<String> errMsgList = new LinkedList<>();
        String locationPattern = "classpath*:com/xs/module/constants/*.class";
        String msgPrefix = ConstantsConfig.get("LOG_MSG_PREFIX");
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
                            String errMsg = msgPrefix + "数据库表`" + tableName + "`不存在";
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
                                    String errMsg = msgPrefix + "数据库表`" + tableName + "`字段`" + columnName + "`不存在";
                                    errMsgList.add(errMsg);
                                }
                            } else {
                                String errMsg = msgPrefix + "[" + clazz.getName() + "]类中的[" + fieldName + "]字段无@" + ColumnCheck.class.getSimpleName() + "注解";
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