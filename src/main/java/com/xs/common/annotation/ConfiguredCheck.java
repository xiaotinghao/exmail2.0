package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.*;
import com.xs.common.utils.spring.SpringTool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.*;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;

/**
 * 自定义常量数据表字段配置校验注解
 * 在常量类上添加该注解后，会对该常量类与数据库表的特定字段进行校验
 *
 * @author 18871430207@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfiguredCheck {

    /**
     * 注解扫描路径
     */
    String scanPath = PropertyUtils.getProperties("annotation.properties")
            .getProperty("ConfiguredCheck_scanPath");

    /**
     * 校验的数据库表名
     */
    String tableName();

    /**
     * 校验的字段名
     */
    String columnName();

    class Utils {

        /**
         * 校验数据常量是否已在数据库中配置
         *
         * @throws RuntimeException 抛出运行时异常
         */
        public static void checkConfigured() throws RuntimeException {
            BaseDao baseDao = SpringTool.getBean(BaseDao.class);
            List<String> errMsgList = new LinkedList<>();
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    ConfiguredCheck annotation = clazz.getAnnotation(ConfiguredCheck.class);
                    if (annotation != null) {
                        // 获取校验表名称
                        String tableName = annotation.tableName();
                        // 获取校验字段名称
                        String columnName = annotation.columnName();
                        // 校验数据表是否存在
                        String tableCheckResult = baseDao.checkTable(tableName);
                        if (StringUtils.isEmpty(tableCheckResult)) {
                            String errMsg = "[" + clazz.getName() + "]类对应的数据库表" +
                                    "`" + tableName + "`不存在";
                            errMsgList.add(errMsg);
                        } else {
                            // 校验数据表字段是否存在
                            String columnCheckResult = baseDao.checkColumn(tableName, columnName);
                            if (StringUtils.isEmpty(columnCheckResult)) {
                                String errMsg = "[" + clazz.getName() + "]类对应的数据库表" +
                                        "`" + tableName + "`的字段`" + columnName + "`不存在";
                                errMsgList.add(errMsg);
                            } else {
                                // 1、存在内部类是，校验内部类的属性是否与表字段匹配
                                // 2、校验clazz对象的属性是否在tableName表中配置有相应的值
                                errMsgList.addAll(check(tableName, columnName, clazz));
                            }
                        }
                    }
                }
            }
            if (!errMsgList.isEmpty()) {
                throw new RuntimeException(CollectionUtils.toString(errMsgList, LINE_BREAK + TAB));
            }
        }

        /**
         * 1、存在内部类是，校验内部类的属性是否与表字段匹配
         * 2、校验clazz对象的属性是否在tableName表中配置有相应的值
         *
         * @param tableName  表名称
         * @param columnName 字段名称
         * @param clazz      Class对象
         * @return 异常信息，无异常时返回空集合
         */
        private static List<String> check(String tableName, String columnName, Class<?> clazz) {
            List<String> errMsgList = new LinkedList<>();
            BaseDao baseDao = SpringTool.getBean(BaseDao.class);
            Object obj = ClassUtils.newInstance(clazz);
            Field[] fields = obj.getClass().getFields();
            List<String> columnValues = baseDao.listColumnValues(tableName, columnName);
            if (fields.length > 0) {
                Set<Class<?>> checkedSet = new HashSet<>();
                for (Field field : fields) {
                    Class<?> typeClass = field.getType();
                    // 数据类型不是基本类型或包装类型，需额外校验
                    if (!XsUtils.isPrimitiveOrPackaged(typeClass)) {
                        if (!checkedSet.contains(typeClass)) {
                            checkedSet.add(typeClass);
                            // 校验tableName表字段是否与typeClass对象的属性匹配
                            errMsgList.addAll(TableFieldCheck.Utils.checkField(tableName, typeClass));
                        }
                    }
                    String fieldName = field.getName();
                    if (!columnValues.contains(fieldName)) {
                        // 属性值未在表中配置
                        String errMsg = "[" + clazz.getName() + "]表的[" + fieldName + "]属性值未在数据库表" +
                                "`" + tableName + "`中配置";
                        errMsgList.add(errMsg);
                    }
                }
            }
            return errMsgList;
        }

    }

}
