package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.ClassUtils;
import com.xs.common.utils.CollectionUtils;
import com.xs.common.utils.PropertyUtils;
import com.xs.common.utils.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;

/**
 * 自定义数据表&表字段校验注解
 * 在常量类上添加该注解后，会对该常量类与数据库表进行一致性校验
 *
 * @author 18871430207@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableFieldCheck {

    /**
     * 注解扫描路径
     */
    String scanPath = PropertyUtils.getProperties("annotation.properties")
            .getProperty("TableFieldCheck_scanPath");

    /**
     * 校验的数据库表名
     */
    String tableName();

    class Utils {

        /**
         * 常量类与数据库表进行一致性校验
         *
         * @param applicationContext Spring上下文对象
         * @throws RuntimeException 抛出运行时异常
         */
        public static void checkTableField(WebApplicationContext applicationContext) throws RuntimeException {
            List<String> errMsgList = new LinkedList<>();
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    TableFieldCheck annotation = clazz.getAnnotation(TableFieldCheck.class);
                    if (annotation != null) {
                        // 获取表名称
                        String tableName = annotation.tableName();
                        errMsgList.addAll(check(applicationContext, tableName, clazz));
                    }
                }
            }
            if (!errMsgList.isEmpty()) {
                throw new RuntimeException(CollectionUtils.toString(errMsgList, LINE_BREAK + TAB));
            }
        }

        /**
         * 1、校验clazz对应的tableName表是否存在
         * 2、校验tableName表字段是否与clazz对应的Class对象的属性对应
         *
         * @param tableName 表名称
         * @param clazz     Class类
         * @return 异常信息，无异常时返回空集合
         */
        public static List<String> check(WebApplicationContext applicationContext, String tableName, Class<?> clazz) {
            List<String> errMsgList = new LinkedList<>();
            BaseDao baseDao = applicationContext.getBean(BaseDao.class);
            // 查询数据表是否存在
            String tableCheckResult = baseDao.checkTable(tableName);
            if (StringUtils.isEmpty(tableCheckResult)) {
                String errMsg = "[" + clazz.getName() + "]常量类对应的数据库表`"
                        + tableName + "`不存在";
                errMsgList.add(errMsg);
            } else {
                // 表存在，则查询表的所有字段
                List<String> columns = baseDao.listColumns(tableName);
                // 获取常量字段，检验是否数据表字段是否对应
                Field[] fields = clazz.getFields();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    if (!columns.contains(fieldName)) {
                        String errMsg = "[" + clazz.getName() + "." + fieldName + "]常量与数据库表`"
                                + tableName + "`字段不匹配";
                        errMsgList.add(errMsg);
                    }
                }
            }
            return errMsgList;
        }

    }

}
