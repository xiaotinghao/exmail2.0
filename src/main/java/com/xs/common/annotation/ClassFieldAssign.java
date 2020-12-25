package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.dao.ConstantsDao;
import com.xs.common.utils.*;
import com.xs.common.utils.spring.SpringTool;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;

/**
 * 自定义类的属性赋值注解@ClassFieldAssign
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
public @interface ClassFieldAssign {

    /**
     * <p> 注解扫描路径 </p>
     * 可配置，示例：ClassFieldAssign.scanPath=classpath*:com/** /constants/** /*.class
     */
    String scanPath = PropertyUtils.list().getProperty("ClassFieldAssign.scanPath1");

    /**
     * 校验的数据库表名
     */
    String tableName();

    /**
     * 属性名对应的数据库字段
     */
    String keyColumn() default "constants_key";

    /**
     * 属性取值对应的数据库字段
     */
    String valueColumn() default "constants_value";

    class Utils {

        private static BaseDao baseDao = SpringTool.getBean(BaseDao.class);

        private static String scanPathMissing_template;
        private static String tableNotExists_template;

        static {
            String fileName = "file/annotationMsg.txt";
            scanPathMissing_template = PropertyUtils.getProperties(fileName).getProperty("scanPathMissing");
            tableNotExists_template = PropertyUtils.getProperties(fileName).getProperty("tableNotExists");
        }

        /**
         * 对类的属性进行赋值
         */
        public static void assign() throws RuntimeException {
            List<String> errMsgList = new LinkedList<>();
            if (StringUtils.isEmpty(scanPath)) {
                String annotationName = ClassFieldAssign.class.getSimpleName();
                String errMsg = String.format(scanPathMissing_template, LINE_BREAK, TAB, annotationName, annotationName);
                throw new RuntimeException(errMsg);
            }
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    ClassFieldAssign annotation = clazz.getAnnotation(ClassFieldAssign.class);
                    if (annotation != null) {
                        // 校验数据常量是否已在数据库中配置
                        List<String> errMsgList2 = checkConfigured(clazz, annotation);
                        if (errMsgList2.isEmpty()) {
                            // 对象字段值赋值
                            errMsgList.addAll(assign(clazz, annotation));
                        } else {
                            errMsgList.addAll(errMsgList2);
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
        private static List<String> checkTableField(Class<?> clazz, String tableName) {
            List<String> errMsgList = new LinkedList<>();
            // 校验clazz对应的tableName表是否存在
            String tableCheckResult = baseDao.checkTable(tableName);
            if (StringUtils.isEmpty(tableCheckResult)) {
                String errMsg = String.format(tableNotExists_template, clazz.getName(), tableName);
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
            String keyColumn = annotation.keyColumn();
            // 校验clazz对应的tableName表是否存在
            String tableCheckResult = baseDao.checkTable(tableName);
            if (StringUtils.isEmpty(tableCheckResult)) {
                String errMsg = "[" + clazz.getName() + "]类对应的数据库表" +
                        "`" + tableName + "`不存在";
                errMsgList.add(errMsg);
            } else {
                // 校验数据表字段是否存在
                String checkResult = baseDao.checkColumn(tableName, keyColumn);
                if (StringUtils.isEmpty(checkResult)) {
                    String errMsg = "[" + clazz.getName() + "]类对应的数据库表" +
                            "`" + tableName + "`的属性名映射字段`" + keyColumn + "`不存在";
                    errMsgList.add(errMsg);
                } else {
                    // 1、存在内部类是，校验内部类的属性是否与表字段匹配
                    // 2、校验clazz对象的属性是否在tableName表中配置有相应的值
                    errMsgList.addAll(checkConfigured(tableName, keyColumn, clazz));
                }
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
                        // 校验类及其属性与数据库表的一致性
                        errMsgList.addAll(checkTableField(typeClass, tableName));
                    }
                }
                String fieldName = field.getName();
                if (!columnValues.contains(fieldName)) {
                    // 属性值未在表中配置
                    String errMsg = "[" + clazz.getName() + "]类的[" + fieldName + "]属性值未在数据库表" +
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
        private static List<String> assign(Class<?> clazz, ClassFieldAssign annotation) {
            List<String> errMsgList = new LinkedList<>();
            String tableName = annotation.tableName();
            String keyColumn = annotation.keyColumn();
            String valueColumn = annotation.valueColumn();
            ConstantsDao constantsDao = SpringTool.getBean(ConstantsDao.class);
            Object obj = ClassUtils.newInstance(clazz);
            Field[] fields = obj.getClass().getFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Map<String, Object> constant = constantsDao.getConstantByKey(tableName, keyColumn, fieldName);
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
                        if (!constant.containsKey(valueColumn)) {
                            String errMsg = "[" + clazz.getName() + "]类对应的数据库表" +
                                    "`" + tableName + "`的属性值映射字段`" + valueColumn + "`不存在";
                            errMsgList.add(errMsg);
                            return errMsgList;
                        }
                        Class packagedClass = type;
                        // 如果Class对象为表示八个基本类型，则转换为包装类型
                        if (type.isPrimitive()) {
                            packagedClass = XsUtils.getPackagedClass(XsUtils.cast(type));
                        }
                        String stringValue = String.valueOf(constant.get(valueColumn));
                        Object value = XsUtils.cast(packagedClass, stringValue);
                        XsUtils.setFieldValue(field, obj, value);
                    }
                }
            }
            return errMsgList;
        }

    }

}