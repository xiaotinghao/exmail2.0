package com.xs.common.annotation;

import com.xs.common.utils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 注解基础类
 *
 * @author 18871430207@163.com
 */
class AnnotationBase {

    static String SCAN_PATH_MISSING_TEMPLATE;
    static String TABLE_NOT_EXISTS_TEMPLATE;
    static String COLUMN_NOT_EXISTS_TEMPLATE;
    static String FIELD_NOT_CONFIGURED_TEMPLATE;
    static String FIELD_UNMATCHED_TEMPLATE;
    static String FIELD_VALUE_UNMATCHED_TEMPLATE;

    static {
        // 获取resources目录下的全部配置
        Properties properties = PropertyUtils.list();
        try {
            AnnotationBase obj = AnnotationBase.class.newInstance();
            Field[] fields = AnnotationBase.class.getDeclaredFields();
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
    static String getScanPath(Class<?> clazz) {
        String annotationName = clazz.getSimpleName();
        return PropertyUtils.list().getProperty(annotationName + ".scanPath");
    }

}