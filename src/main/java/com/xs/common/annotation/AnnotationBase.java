package com.xs.common.annotation;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.CollectionUtils;
import com.xs.common.utils.PropertyUtils;
import daisy.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;

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
    /**
     * 定义异常信息分隔符
     */
    private static final String SEPARATOR = LINE_BREAK + TAB;
    /**
     * 获取注解配置
     */
    private static Properties properties = PropertyUtils.getProperties("annotation.properties");
    /**
     * 注解扫描路径配置查询关键词
     */
    private static final String SCAN_PATH_PREFIX = ".scanPath";

    public static String SCAN_PATH_MISSING_TEMPLATE;
    public static String TABLE_NOT_EXISTS_TEMPLATE;
    public static String COLUMN_NOT_EXISTS_TEMPLATE;
    public static String FIELD_NOT_CONFIGURED_TEMPLATE;

    static {
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
     * 检测程序是否已出错
     */
    static void checkError() {
        if (!errMsgList.isEmpty()) {
            throw new RuntimeException(CollectionUtils.toString(errMsgList, SEPARATOR));
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

    static List<Class<?>> getClasses(Class<?> annotationClass) {
        // 获取注解扫描路径
        List<String> pathList = getScanPath(annotationClass);
        // 获取注解扫描的Class对象
        return ClassUtils.getClasses(pathList);
    }

    /**
     * 获取注解的扫描路径
     *
     * @param annotationClass 注解的Class对象
     * @return 注解的扫描路径
     */
    private static List<String> getScanPath(Class<?> annotationClass) {
        String annotationName = annotationClass.getSimpleName();
        String keyPattern = annotationName + SCAN_PATH_PREFIX;
        List<String> pathList = new LinkedList<>();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = String.valueOf(keys.nextElement());
            if (key.startsWith(keyPattern)) {
                pathList.add(properties.getProperty(key));
            }
        }
        if (pathList.isEmpty()) {
            String errMsg = String.format(SCAN_PATH_MISSING_TEMPLATE, LINE_BREAK, TAB, annotationName, annotationName);
            throw new RuntimeException(errMsg);
        }
        return pathList;
    }

}