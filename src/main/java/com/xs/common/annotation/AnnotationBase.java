package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * 注解基础类
 *
 * @author 18871430207@163.com
 */
public class AnnotationBase {

    /**
     * 基础业务数据服务对象
     */
    static BaseDao baseDao;
    /**
     * 定义异常信息结果集
     */
    static List<String> errMsgList = new LinkedList<>();

    public static String SCAN_PATH_MISSING_TEMPLATE;
    public static String TABLE_NOT_EXISTS_TEMPLATE;
    public static String COLUMN_NOT_EXISTS_TEMPLATE;
    public static String FIELD_NOT_CONFIGURED_TEMPLATE;

    static {
        // 获取resources目录下的全部配置
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

    /**
     * 初始化数据服务对象
     *
     * @param baseDao 基础数据服务对象
     */
    public static void init(BaseDao baseDao) {
        // 初始化数据服务对象
        AnnotationBase.baseDao = baseDao;
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