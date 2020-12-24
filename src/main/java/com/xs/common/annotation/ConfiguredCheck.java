package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.dao.ConstantsDao;
import com.xs.common.utils.*;
import com.xs.common.utils.spring.SpringTool;
import org.springframework.web.context.WebApplicationContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
         * @param applicationContext Spring上下文对象
         * @throws RuntimeException 抛出运行时异常
         */
        public static void checkConfigured(WebApplicationContext applicationContext) throws RuntimeException {
            BaseDao baseDao = applicationContext.getBean(BaseDao.class);
            List<String> errMsgList = new LinkedList<>();
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    ConfiguredCheck annotation = clazz.getAnnotation(ConfiguredCheck.class);
                    if (annotation != null) {
                        // 刷新常量类的字段值
                        initConstants(clazz);
                        // 获取表名称
                        String tableName = annotation.tableName();
                        // 获取字段名称
                        String columnName = annotation.columnName();
                        // 校验数据表是否存在
                        String tableCheckResult = baseDao.checkTable(tableName);
                        if (StringUtils.isEmpty(tableCheckResult)) {
                            String errMsg = "[" + clazz.getName() + "]常量类对应的数据库表`"
                                    + tableName + "`不存在";
                            errMsgList.add(errMsg);
                        } else {
                            // 校验数据表字段是否存在
                            String columnCheckResult = baseDao.checkColumn(tableName, columnName);
                            if (StringUtils.isEmpty(columnCheckResult)) {
                                String errMsg = "[" + clazz.getName() + "]常量类对应的数据库表`"
                                        + tableName + "`的字段`" + columnName + "`不存在";
                                errMsgList.add(errMsg);
                            } else {
                                Object obj = ClassUtils.newInstance(clazz);
                                Field[] fields = obj.getClass().getFields();
                                List<String> columnValues = baseDao.listColumnValues(tableName, columnName);
                                if (fields.length > 0) {
                                    Class<?> type = fields[0].getType();
                                    // 判断数据类型是否为基本类型或包装类型
                                    if (!XsUtils.isPrimitiveOrPackaged(type)) {
                                        errMsgList.addAll(TableFieldCheck.Utils.check(applicationContext, tableName, type));
                                    }
                                    for (Field field : fields) {
                                        String fieldName = field.getName();
                                        if (!columnValues.contains(fieldName)) {
                                            // 常量未在表中配置
                                            String errMsg = "[" + clazz.getName() + "." + fieldName + "]常量未在数据库表`"
                                                    + tableName + "`中配置";
                                            errMsgList.add(errMsg);
                                        }
                                    }
                                }
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
         * 刷新常量配置
         */
        public static void refreshConstants() {
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    ConfiguredCheck annotation = clazz.getAnnotation(ConfiguredCheck.class);
                    if (annotation != null) {
                        initConstants(clazz);
                    }
                }
            }
        }

        /**
         * 加载对象字段值
         *
         * @param clazz Class类对象
         */
        private static void initConstants(Class<?> clazz) {
            ConfiguredCheck annotation = clazz.getAnnotation(ConfiguredCheck.class);
            if (annotation != null) {
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

}
