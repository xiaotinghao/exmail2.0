package com.xs.commons.lang3;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.PropertyUtils;
import com.xs.common.utils.StringUtils;
import com.xs.common.utils.spring.SpringTool;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * 注解工具类
 *
 * @author 18871430207@163.com
 */
public class AnnotationUtils extends org.apache.commons.lang3.AnnotationUtils {

    public static String SCAN_PATH_MISSING_TEMPLATE;
    public static String TABLE_NOT_EXISTS_TEMPLATE;
    public static String COLUMN_NOT_EXISTS_TEMPLATE;
    public static String FIELD_NOT_CONFIGURED_TEMPLATE;
    public static String FIELD_UNMATCHED_TEMPLATE;
    public static String FIELD_VALUE_UNMATCHED_TEMPLATE;

    protected static BaseDao baseDao = SpringTool.getBean(BaseDao.class);

    static {
        // 获取resources目录下的全部配置
        Properties properties = PropertyUtils.list();
        try {
            AnnotationUtils obj = AnnotationUtils.class.newInstance();
            Field[] fields = AnnotationUtils.class.getFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                field.set(obj, properties.getProperty(fieldName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p> 获取注解的扫描路径 </p>
     * 可配置，示例：annotationName.scanPath=classpath*:com/** /constants/** /*.class
     *
     * @param clazz 注解的Class对象
     * @return 注解的扫描路径
     */
    public static String getScanPath(Class<?> clazz) {
        String annotationName = clazz.getSimpleName();
        return PropertyUtils.list().getProperty(annotationName + ".scanPath");
    }

    /**
     * 校验类及其属性与数据库表的一致性
     *
     * @param clazz     Class对象
     * @param tableName 表名称
     * @return 异常信息，无异常时返回空集合
     */
    protected static List<String> checkTable(Class<?> clazz, String tableName) {
        List<String> errMsgList = new LinkedList<>();
        // 校验clazz对应的tableName表是否存在
        String tableCheckResult = baseDao.checkTable(tableName);
        if (StringUtils.isEmpty(tableCheckResult)) {
            String errMsg = String.format(TABLE_NOT_EXISTS_TEMPLATE, clazz.getName(), tableName);
            errMsgList.add(errMsg);
        } else {
            // 校验tableName表字段是否与clazz对象的属性匹配
            errMsgList.addAll(AnnotationUtils.checkField(tableName, clazz));
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

}