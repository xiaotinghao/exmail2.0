package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.*;
import com.xs.common.utils.spring.SpringTool;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;

/**
 * 自定义匹配数据库表的注解@MatchTable
 * <p> tableName   用于设置表名 </p>
 * <p> 在常量类上添加该注解后，会 </p>
 * <p> 1、校验类及其属性与数据库表的一致性 </p>
 * <p> 2、对类的属性进行赋值 </p>
 *
 * @author 18871430207@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MatchTable {

    /**
     * <p> 注解扫描路径 </p>
     * 可配置，示例：MatchTable.scanPath=classpath*:com/** /constants/** /*.class
     */
    String scanPath = PropertyUtils.list().getProperty("MatchTable.scanPath");

    /**
     * 数据库表名
     */
    String tableName();

    class Utils {

        private static BaseDao baseDao = SpringTool.getBean(BaseDao.class);

        /**
         * 对类的属性进行赋值
         */
        public static void assign() throws RuntimeException {
            List<String> errMsgList = new LinkedList<>();
            if (StringUtils.isEmpty(scanPath)) {
                String errMsg = LINE_BREAK + TAB + "系统已使用@MatchTable注解，但未在配置文件中发现MatchTable.scanPath";
                throw new RuntimeException(errMsg);
            }
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    MatchTable annotation = clazz.getAnnotation(MatchTable.class);
                    if (annotation != null) {
                        String tableName = annotation.tableName();
                        // 校验类及其属性与数据库表的一致性
                        List<String> errMsgList1 = checkTable(clazz, tableName);
                        if (errMsgList1.isEmpty()) {
                            errMsgList.addAll(assign(clazz));
                        } else {
                            errMsgList.addAll(errMsgList1);
                        }
                    }
                }
            }
            if (!errMsgList.isEmpty()) {
                throw new RuntimeException(CollectionUtils.toString(errMsgList, LINE_BREAK + TAB));
            }
        }

        /**
         * 校验类及其属性与数据库表的一致性
         *
         * @param clazz     Class对象
         * @param tableName 表名称
         * @return 异常信息，无异常时返回空集合
         */
        private static List<String> checkTable(Class<?> clazz, String tableName) {
            List<String> errMsgList = new LinkedList<>();
            // 校验clazz对应的tableName表是否存在
            String tableCheckResult = baseDao.checkTable(tableName);
            if (StringUtils.isEmpty(tableCheckResult)) {
                String errMsg = "[" + clazz.getName() + "]类对应的数据库表" +
                        "`" + tableName + "`不存在";
                errMsgList.add(errMsg);
            } else {
                // 校验tableName表字段是否与clazz对象的属性匹配
                errMsgList.addAll(checkColumn(tableName, clazz));
            }
            return errMsgList;
        }

        /**
         * 校验tableName表字段是否与clazz对象的属性匹配
         *
         * @param tableName 表名称
         * @param clazz     Class对象
         * @return 异常信息，无异常时返回空集合
         */
        private static List<String> checkColumn(String tableName, Class<?> clazz) {
            List<String> errMsgList = new LinkedList<>();
            // 查询数据表是否存在
            String tableCheckResult = baseDao.checkTable(tableName);
            if (!StringUtils.isEmpty(tableCheckResult)) {
                // 表存在，则查询表的所有字段
                List<String> columns = baseDao.listColumns(tableName);
                // 获取常量字段，检验是否数据表字段是否对应
                Field[] fields = clazz.getFields();
                for (Field field : fields) {
                    // 获取属性名
                    String fieldName = field.getName();
                    // 获取@Column注解的值
                    if (field.isAnnotationPresent(Value.class)) {
                        String columnName = field.getAnnotation(Value.class).value();
                        if (!columns.contains(columnName)) {
                            String errMsg = "[" + clazz.getName() + "]类的" +
                                    "[" + fieldName + "]属性映射字段`" + columnName
                                    + "`与数据库表`" + tableName + "`的字段名不匹配";
                            errMsgList.add(errMsg);
                        }
                    } else {
                        if (!columns.contains(fieldName)) {
                            String errMsg = "[" + clazz.getName() + "]类的" +
                                    "[" + fieldName + "]属性名与数据库表" +
                                    "`" + tableName + "`的字段名不匹配";
                            errMsgList.add(errMsg);
                        }
                    }
                }
            }
            return errMsgList;
        }

        /**
         * 对类的属性进行赋值
         *
         * @param clazz Class对象
         */
        private static List<String> assign(Class<?> clazz) {
            List<String> errMsgList = new LinkedList<>();
            try {
                Object obj = ClassUtils.newInstance(clazz);
                Field[] fields = obj.getClass().getFields();
                for (Field field : fields) {
                    Object value;
                    if (field.isAnnotationPresent(Value.class)) {
                        value = field.getAnnotation(Value.class).value();
                    } else {
                        value = field.getName();
                    }
                    XsUtils.setFieldValue(field, obj, value);
                }
            } catch (Exception e) {
                String errMsg = e.getMessage() + e.getCause();
                errMsgList.add(errMsg);
            }
            return errMsgList;
        }

    }

}
