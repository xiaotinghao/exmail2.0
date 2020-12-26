package com.xs.common.annotation.constants;

import com.xs.common.utils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 注解常量类，类的属性与resources目录下的配置文件中的key对应
 *
 * @author 18871430207@163.com
 */
public class AnnotationBase {

    public static String SCAN_PATH_MISSING_TEMPLATE;
    public static String TABLE_NOT_EXISTS_TEMPLATE;
    public static String COLUMN_NOT_EXISTS_TEMPLATE;
    public static String FIELD_NOT_CONFIGURED_TEMPLATE;
    public static String FIELD_UNMATCHED_TEMPLATE;
    public static String FIELD_VALUE_UNMATCHED_TEMPLATE;

    static {
        Properties properties = PropertyUtils.list();
        try {
            AnnotationBase obj = AnnotationBase.class.newInstance();
            Field[] fields = AnnotationBase.class.getFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                field.set(obj, properties.getProperty(fieldName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
