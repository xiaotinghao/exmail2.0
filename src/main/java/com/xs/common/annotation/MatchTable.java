package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.StringUtils;
import com.xs.common.utils.*;
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
     * 数据库表名
     */
    String tableName();

    class Utils extends AnnotationBase {

        /**
         * 对类的属性进行赋值
         */
        public static void assign(BaseDao baseDao) throws RuntimeException {
            // 获取注解的Class对象
            Class<MatchTable> annotationClass = MatchTable.class;
            // 注解扫描路径
            String scanPath = getScanPath(annotationClass);
            List<String> errMsgList = new LinkedList<>();
            if (StringUtils.isEmpty(scanPath)) {
                String annotationName = annotationClass.getSimpleName();
                String errMsg = String.format(SCAN_PATH_MISSING_TEMPLATE, LINE_BREAK, TAB, annotationName, annotationName);
                throw new RuntimeException(errMsg);
            }
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    MatchTable annotation = clazz.getAnnotation(annotationClass);
                    if (annotation != null) {
                        String tableName = annotation.tableName();
                        // 校验类及其属性与数据库表的一致性
                        List<String> errMsgList1 = checkTable(clazz, tableName, baseDao);
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
        static List<String> checkTable(Class<?> clazz, String tableName, BaseDao baseDao) {
            List<String> errMsgList = new LinkedList<>();
            // 校验clazz对应的tableName表是否存在
            String tableCheckResult = baseDao.checkTable(tableName);
            if (StringUtils.isEmpty(tableCheckResult)) {
                String errMsg = String.format(TABLE_NOT_EXISTS_TEMPLATE, clazz.getName(), tableName);
                errMsgList.add(errMsg);
            } else {
                // 校验tableName表字段是否与clazz对象的属性匹配
                errMsgList.addAll(checkField(clazz, tableName, baseDao));
            }
            return errMsgList;
        }

        /**
         * 校验tableName表字段是否与clazz对象的属性匹配
         *
         * @param clazz     Class对象
         * @param tableName 表名称
         * @return 异常信息，无异常时返回空集合
         */
        private static List<String> checkField(Class<?> clazz, String tableName, BaseDao baseDao) {
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
                    // 获取@Value注解的值
                    if (field.isAnnotationPresent(Value.class)) {
                        String columnName = field.getAnnotation(Value.class).value();
                        if (!columns.contains(columnName)) {
                            String errMsg = String.format(FIELD_VALUE_UNMATCHED_TEMPLATE, clazz.getName(), fieldName, columnName, tableName);
                            errMsgList.add(errMsg);
                        }
                    } else {
                        if (!columns.contains(fieldName)) {
                            String errMsg = String.format(FIELD_UNMATCHED_TEMPLATE, clazz.getName(), fieldName, tableName);
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
