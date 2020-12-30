package com.xs.common.annotation;

import daisy.commons.lang3.ClassUtils;
import daisy.commons.lang3.StringUtils;
import com.xs.common.utils.*;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;

/**
 * 自定义类的属性赋值注解@Assign
 * <p> tableName   用于设置表名 </p>
 * <p> keyColumn   用于设置属性名对应的数据库字段 </p>
 * <p> valueColumn 用于设置属性取值对应的数据库字段 </p>
 * <p> 在常量类上添加该注解后，会 </p>
 * <p> 1、校验类及其属性与数据库表的一致性 </p>
 * <p> 2、校验类的属性值是否已在数据库中配置 </p>
 * <p> 3、对类的属性进行赋值 </p>
 *
 * @author 18871430207@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Assign {

    /**
     * 校验的数据库名称
     */
    String tableSchema();

    /**
     * 校验的数据表名称
     */
    String tableName();

    /**
     * 属性名对应的数据库字段
     */
    String keyColumn();

    /**
     * 属性取值对应的数据库字段
     */
    String valueColumn();

    class Utils extends AnnotationBase {

        /**
         * 对类的属性进行赋值
         */
        public static void assign() throws RuntimeException {
            // 注解扫描路径
            String scanPath = getScanPath(Assign.class);
            if (StringUtils.isBlank(scanPath)) {
                String annotationName = Assign.class.getSimpleName();
                String errMsg = String.format(SCAN_PATH_MISSING_TEMPLATE, LINE_BREAK, TAB, annotationName, annotationName);
                throw new RuntimeException(errMsg);
            }
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            // 校验注解配置的表和字段是否存在
            checkExists(classes);
            if (!errMsgList.isEmpty()) {
                throw new RuntimeException(CollectionUtils.toString(errMsgList, LINE_BREAK + TAB));
            }
            // 校验类的属性是否在tableName表中配置有相应的值
            checkConfigured(classes);
            if (!errMsgList.isEmpty()) {
                throw new RuntimeException(CollectionUtils.toString(errMsgList, LINE_BREAK + TAB));
            }
            // 对类的属性进行赋值
            assign(classes);
        }

        /**
         * 校验注解配置的表和字段是否存在
         *
         * @param classes Class对象集合
         */
        private static void checkExists(List<Class<?>> classes) {
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    checkExists(clazz);
                }
            }
        }

        /**
         * 校验注解配置的表和字段是否存在
         *
         * @param clazz Class对象
         */
        private static void checkExists(Class<?> clazz) {
            Assign annotation = clazz.getAnnotation(Assign.class);
            if (annotation != null) {
                // 获取数据库名称
                String tableSchema = annotation.tableSchema();
                // 获取数据表名称
                String tableName = annotation.tableName();
                // 校验clazz对应的tableName表是否存在
                String tableCheckResult = baseDao.checkTable(tableSchema, tableName);
                if (StringUtils.isBlank(tableCheckResult)) {
                    String errMsg = String.format(TABLE_NOT_EXISTS_TEMPLATE, clazz.getName(), tableName);
                    errMsgList.add(errMsg);
                } else {
                    // 校验属性名对应的数据库字段是否存在
                    checkColumnExists(clazz, tableSchema, tableName, annotation.keyColumn());
                    // 校验属性取值对应的数据库字段是否存在
                    checkColumnExists(clazz, tableSchema, tableName, annotation.valueColumn());
                }
            }
        }

        /**
         * 校验类的属性值是否已在数据库中配置
         *
         * @param clazz       Class对象
         * @param tableSchema 数据库名称
         * @param tableName   数据表名称
         * @param columnName  字段名
         */
        private static void checkColumnExists(Class<?> clazz, String tableSchema, String tableName, String columnName) {
            // 校验数据表字段是否存在
            String valueColumnCheckResult = baseDao.checkColumn(tableSchema, tableName, columnName);
            if (StringUtils.isBlank(valueColumnCheckResult)) {
                String errMsg = String.format(COLUMN_NOT_EXISTS_TEMPLATE, clazz.getName(), tableName, columnName);
                errMsgList.add(errMsg);
            }
        }

        /**
         * 校验类的属性是否在tableName表中配置有相应的值
         *
         * @param classes Class对象集合
         */
        private static void checkConfigured(List<Class<?>> classes) {
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    checkConfigured(clazz);
                }
            }
        }

        /**
         * 校验类的属性是否在tableName表中配置有相应的值
         *
         * @param clazz Class对象
         */
        private static void checkConfigured(Class<?> clazz) {
            Assign annotation = clazz.getAnnotation(Assign.class);
            if (annotation != null) {
                String tableSchema = annotation.tableSchema();
                String tableName = annotation.tableName();
                String keyColumn = annotation.keyColumn();
                Object obj = ClassUtils.newInstance(clazz);
                Field[] fields = obj.getClass().getFields();
                List<String> columnValues = baseDao.listColumnValues(tableSchema, tableName, keyColumn);
                // 定义已校验的Class类的集合
                Set<Class<?>> checkedSet = new HashSet<>();
                for (Field field : fields) {
                    // 校验类及其属性与数据库表的一致性
                    checkFieldMatch(field, checkedSet, tableSchema, tableName);
                    // 校验类的属性值是否在表中配置
                    checkFieldConfigured(clazz, tableName, field, columnValues);
                }
            }
        }

        /**
         * 校验类及其属性与数据库表的一致性
         *
         * @param field       类的属性
         * @param checkedSet  已校验的Class类的集合
         * @param tableSchema 数据库名称
         * @param tableName   数据表名称
         */
        private static void checkFieldMatch(Field field, Set<Class<?>> checkedSet, String tableSchema, String tableName) {
            Class<?> typeClass = field.getType();
            // 数据类型不是基本类型或包装类型，需额外校验
            if (!ClassUtils.isPrimitiveOrWrapper(typeClass)) {
                if (!checkedSet.contains(typeClass)) {
                    checkedSet.add(typeClass);
                    // 校验类及其属性与数据库表的一致性
                    Table.Utils.checkExists(typeClass, tableSchema, tableName);
                }
            }
        }

        /**
         * 校验类的属性值是否在表中配置
         *
         * @param field        类的属性
         * @param clazz        Class对象
         * @param tableName    表名
         * @param columnValues 字段名
         */
        private static void checkFieldConfigured(Class<?> clazz, String tableName, Field field, List<String> columnValues) {
            String columnName;
            if (field.isAnnotationPresent(Key.class)) {
                columnName = field.getAnnotation(Key.class).keyName();
            } else {
                columnName = field.getName();
            }
            if (!columnValues.contains(columnName)) {
                // 属性值未在表中配置
                String errMsg = String.format(FIELD_NOT_CONFIGURED_TEMPLATE, clazz.getName(), columnName, tableName);
                errMsgList.add(errMsg);
            }
        }

        /**
         * 对类的属性进行赋值
         *
         * @param classes Class对象集合
         */
        private static void assign(List<Class<?>> classes) {
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
        private static void assign(Class<?> clazz) {
            Assign annotation = clazz.getAnnotation(Assign.class);
            if (annotation != null) {
                String tableSchema = annotation.tableSchema();
                String tableName = annotation.tableName();
                String keyColumn = annotation.keyColumn();
                String valueColumn = annotation.valueColumn();
                Object obj = ClassUtils.newInstance(clazz);
                Field[] fields = obj.getClass().getFields();
                for (Field field : fields) {
                    String keyName;
                    if (field.isAnnotationPresent(Key.class)) {
                        keyName = field.getAnnotation(Key.class).keyName();
                    } else {
                        keyName = field.getName();
                    }
                    Map<String, Object> constant = baseDao.getByKey(tableSchema, tableName, keyColumn, keyName);
                    if (constant != null) {
                        Class<?> type = field.getType();
                        if (!ClassUtils.isPrimitiveOrWrapper(type)) {
                            // 对象类型（非基础类型&非包装类型）属性赋值
                            objectTypeFieldAssign(type, field, constant, obj);
                        } else {
                            // 基础类型|包装类型属性赋值
                            basicTypeFieldAssign(valueColumn, type, field, constant, obj);
                        }
                    }
                }
            }
        }

        /**
         * 对象类型（非基础类型&非包装类型）属性赋值
         *
         * @param type     属性的变量类型Class对象
         * @param field    属性
         * @param constant 数据库查询数据
         * @param obj      注解对象
         */
        private static void objectTypeFieldAssign(Class<?> type, Field field, Map<String, Object> constant, Object obj) {
            // 获取对象类型属性的对象
            Object typeObj = ClassUtils.newInstance(type);
            // 获取对象类型属性的对象的字段
            Field[] typeFields = type.getFields();
            for (Field typeField : typeFields) {
                String keyName;
                if (typeField.isAnnotationPresent(Key.class)) {
                    keyName = typeField.getAnnotation(Key.class).keyName();
                } else {
                    keyName = typeField.getName();
                }
                if (constant.containsKey(keyName)) {
                    // 基础类型|包装类型的值
                    Object typeValue = constant.get(keyName);
                    // 基础类型|包装类型属性赋值
                    XsUtils.setFieldValue(typeField, typeObj, typeValue);
                }
            }
            // 非基础类型/包装类型属性赋值
            XsUtils.setFieldValue(field, obj, typeObj);
        }

        /**
         * 基础类型|包装类型属性赋值
         *
         * @param valueColumn 注解中配置的属性值映射字段名
         * @param type        属性的变量类型Class对象
         * @param field       属性
         * @param constant    数据库查询数据
         * @param obj         注解对象
         */
        private static void basicTypeFieldAssign(String valueColumn, Class<?> type, Field field, Map<String, Object> constant, Object obj) {
            Class packagedClass = type;
            // 如果Class对象为表示八个基本类型，则转换为包装类型
            if (type.isPrimitive()) {
                packagedClass = ClassUtils.primitiveToWrapper(XsUtils.cast(type));
            }
            String stringValue = String.valueOf(constant.get(valueColumn));
            Object value = XsUtils.cast(packagedClass, stringValue);
            // 基础类型|包装类型属性赋值
            XsUtils.setFieldValue(field, obj, value);
        }

    }

}
