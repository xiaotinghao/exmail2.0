package com.xs.common.annotation;

import daisy.commons.lang3.StringUtils;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 自定义匹配数据库表的注解@Table
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
public @interface Table {
    /**
     * 数据库名
     */
    String tableSchema();

    /**
     * 数据库表名
     */
    String tableName();

    class Utils extends AnnotationBase {

        /**
         * 对类的属性进行赋值
         */
        public static void assign() throws RuntimeException {
            // 获取注解扫描的Class对象
            List<Class<?>> classes = getClasses(Table.class);
            // 校验注解配置的表和字段是否存在
            checkExists(classes);
            try {
                // 对类的属性进行赋值
                assign(classes);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage() + e.getCause());
            }
        }

        /**
         * 校验注解配置的表和字段是否存在
         *
         * @param classes Class对象集合
         */
        private static void checkExists(List<Class<?>> classes) {
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    Table annotation = clazz.getAnnotation(Table.class);
                    if (annotation != null) {
                        // 获取数据库名称
                        String tableSchema = annotation.tableSchema();
                        // 获取数据表名称
                        String tableName = annotation.tableName();
                        // 校验注解配置的表和字段是否存在
                        checkExists(clazz, tableSchema, tableName);
                    }
                }
            }
            // 检测程序是否已出错
            checkError();
        }

        /**
         * 校验注解配置的表和字段是否存在
         *
         * @param clazz Class对象
         */
        static void checkExists(Class<?> clazz, String tableSchema, String tableName) {
            // 校验clazz对应的tableName表是否存在
            String tableCheckResult = baseDao.checkTable(tableSchema, tableName);
            if (StringUtils.isBlank(tableCheckResult)) {
                String errMsg = String.format(TABLE_NOT_EXISTS_TEMPLATE, clazz.getName(), tableName);
                errMsgList.add(errMsg);
            } else {
                // 校验属性名对应的数据库字段是否存在
                checkColumnExists(clazz, tableSchema, tableName);
            }
        }

        /**
         * 校验类的属性值是否已在数据库中配置
         *
         * @param clazz Class对象
         */
        private static void checkColumnExists(Class<?> clazz, String tableSchema, String tableName) {
            List<String> columns = baseDao.listColumns(tableSchema, tableName);
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                String columnName;
                if (field.isAnnotationPresent(Column.class)) {
                    columnName = field.getAnnotation(Column.class).columnName();
                } else {
                    columnName = field.getName();
                }
                // 校验数据表字段是否存在
                if (!columns.contains(columnName)) {
                    String errMsg = String.format(COLUMN_NOT_EXISTS_TEMPLATE, clazz.getName(), tableName, columnName);
                    errMsgList.add(errMsg);
                }
            }
        }

        /**
         * 对类的属性进行赋值
         *
         * @param classes Class对象集合
         */
        private static void assign(List<Class<?>> classes) throws IllegalAccessException, InstantiationException {
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    assign(clazz);
                }
            }
        }

        /**
         * 对类的属性进行赋值
         *
         * @param clazz Class对象
         */
        private static void assign(Class<?> clazz) throws IllegalAccessException, InstantiationException {
            Table annotation = clazz.getAnnotation(Table.class);
            if (annotation != null) {
                Object obj = clazz.newInstance();
                Field[] fields = obj.getClass().getFields();
                for (Field field : fields) {
                    String value;
                    if (field.isAnnotationPresent(Column.class)) {
                        value = field.getAnnotation(Column.class).columnName();
                    } else {
                        value = field.getName();
                    }
                    field.set(obj, value);
                }
            }
        }

    }

}
