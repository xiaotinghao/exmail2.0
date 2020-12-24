package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.dao.ConstantsDao;
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
 * 自定义类的属性赋值注解
 * 在常量类上添加该注解后，会
 * 1、校验类及其属性与数据库表的一致性
 * 2、校验类的属性值是否已在数据库中配置
 * 3、对类的属性进行赋值
 *
 * @author 18871430207@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassFieldAssign {

    /**
     * 注解扫描路径
     */
    String scanPath = PropertyUtils.getProperties("annotation.properties")
            .getProperty("ClassFieldAssign_scanPath");

    /**
     * 校验的数据库表名
     */
    String tableName();

    /**
     * 校验的字段名
     */
    String columnName();

    class Utils {

        private static BaseDao baseDao = SpringTool.getBean(BaseDao.class);

        /**
         * 对类的属性进行赋值
         */
        public static void assign() throws RuntimeException {
            List<String> errMsgList = new LinkedList<>();
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    ClassFieldAssign annotation = clazz.getAnnotation(ClassFieldAssign.class);
                    if (annotation != null) {
                        // 常量类与数据库表进行一致性校验
                        List<String> errMsgList1 = checkTableField(clazz, annotation);
                        if (errMsgList1.isEmpty()) {
                            // 校验数据常量是否已在数据库中配置
                            List<String> errMsgList2 = checkConfigured(clazz, annotation);
                            if (errMsgList2.isEmpty()) {
                                // 对象字段值赋值
                                assign(clazz, annotation);
                            } else {
                                errMsgList.addAll(errMsgList2);
                            }
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
         * @param clazz      Class对象
         * @param annotation ClassFieldAssign注解
         * @return 异常信息，无异常时返回空集合
         */
        private static List<String> checkTableField(Class<?> clazz, ClassFieldAssign annotation) {
            List<String> errMsgList = new LinkedList<>();
            // 获取表名称
            String tableName = annotation.tableName();
            // 校验clazz对应的tableName表是否存在
            String tableCheckResult = baseDao.checkTable(tableName);
            if (StringUtils.isEmpty(tableCheckResult)) {
                String errMsg = "[" + clazz.getName() + "]类对应的数据库表" +
                        "`" + tableName + "`不存在";
                errMsgList.add(errMsg);
            } else {
                // 校验tableName表字段是否与clazz对象的属性匹配
                errMsgList.addAll(checkField(tableName, clazz));
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
        private static List<String> checkField(String tableName, Class<?> clazz) {
            List<String> errMsgList = new LinkedList<>();
            BaseDao baseDao = SpringTool.getBean(BaseDao.class);
            // 查询数据表是否存在
            String tableCheckResult = baseDao.checkTable(tableName);
            if (!StringUtils.isEmpty(tableCheckResult)) {
                // 表存在，则查询表的所有字段
                List<String> columns = baseDao.listColumns(tableName);
                // 获取常量字段，检验是否数据表字段是否对应
                Field[] fields = clazz.getFields();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    if (!columns.contains(fieldName)) {
                        String errMsg = "[" + clazz.getName() + "]类的" +
                                "[" + fieldName + "]属性名与数据库表" +
                                "`" + tableName + "`的字段名不匹配";
                        errMsgList.add(errMsg);
                    }
                }
            }
            return errMsgList;
        }

        /**
         * 校验类的属性值是否已在数据库中配置
         *
         * @param clazz      Class对象
         * @param annotation ClassFieldAssign注解
         * @return 异常信息，无异常时返回空集合
         */
        private static List<String> checkConfigured(Class<?> clazz, ClassFieldAssign annotation) {
            List<String> errMsgList = new LinkedList<>();
            // 获取校验表名称
            String tableName = annotation.tableName();
            // 获取校验字段名称
            String columnName = annotation.columnName();
            // 校验数据表字段是否存在
            String columnCheckResult = baseDao.checkColumn(tableName, columnName);
            if (StringUtils.isEmpty(columnCheckResult)) {
                String errMsg = "[" + clazz.getName() + "]类对应的数据库表" +
                        "`" + tableName + "`的字段`" + columnName + "`不存在";
                errMsgList.add(errMsg);
            } else {
                // 1、存在内部类是，校验内部类的属性是否与表字段匹配
                // 2、校验clazz对象的属性是否在tableName表中配置有相应的值
                errMsgList.addAll(checkConfigured(tableName, columnName, clazz));
            }
            return errMsgList;

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
        private static List<String> checkConfigured(String tableName, String columnName, Class<?> clazz) {
            List<String> errMsgList = new LinkedList<>();
            Object obj = ClassUtils.newInstance(clazz);
            Field[] fields = obj.getClass().getFields();
            List<String> columnValues = baseDao.listColumnValues(tableName, columnName);
            // 定义已校验的Class类的集合
            Set<Class<?>> checkedSet = new HashSet<>();
            for (Field field : fields) {
                Class<?> typeClass = field.getType();
                // 数据类型不是基本类型或包装类型，需额外校验
                if (!XsUtils.isPrimitiveOrPackaged(typeClass)) {
                    if (!checkedSet.contains(typeClass)) {
                        checkedSet.add(typeClass);
                        // 校验tableName表字段是否与typeClass对象的属性匹配
                        errMsgList.addAll(checkField(tableName, typeClass));
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
            return errMsgList;
        }

        /**
         * 对类的属性进行赋值
         *
         * @param clazz      Class对象
         * @param annotation ClassFieldAssign注解
         */
        private static void assign(Class<?> clazz, ClassFieldAssign annotation) {
            String tableName = annotation.tableName();
            String columnName = annotation.columnName();
            ConstantsDao constantsDao = SpringTool.getBean(ConstantsDao.class);
            Object obj = ClassUtils.newInstance(clazz);
            Field[] fields = obj.getClass().getFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Map<String, Object> constant = constantsDao.getConstantByKey(tableName, columnName, fieldName);
                if (constant != null) {
                    Class<?> type = field.getType();
                    if (!XsUtils.isPrimitiveOrPackaged(type)) {
                        Object typeObj = ClassUtils.newInstance(type);
                        Field[] typeFields = type.getFields();
                        for (Field typeField : typeFields) {
                            String typeFieldName = typeField.getName();
                            if (constant.containsKey(typeFieldName)) {
                                XsUtils.setFieldValue(typeField, typeObj, constant.get(typeFieldName));
                            }
                        }
                        XsUtils.setFieldValue(field, obj, typeObj);
                    } else {
                        Class packagedClass = type;
                        // 如果Class对象为表示八个基本类型，则转换为包装类型
                        if (type.isPrimitive()) {
                            packagedClass = XsUtils.getPackagedClass(type);
                        }
                        String columnKey;
                        Column column = field.getAnnotation(Column.class);
                        if (column != null) {
                            columnKey = column.name();
                        } else {
                            columnKey = Column.defaultColumn;
                        }
                        Object value = XsUtils.cast(packagedClass, String.valueOf(constant.get(columnKey)));
                        XsUtils.setFieldValue(field, obj, value);
                    }
                }
            }
        }

    }

}
