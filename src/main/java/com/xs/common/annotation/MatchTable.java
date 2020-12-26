package com.xs.common.annotation;

import com.xs.commons.lang3.AnnotationUtils;
import com.xs.common.utils.StringUtils;
import com.xs.common.utils.*;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static com.xs.common.constants.SymbolConstants.LINE_BREAK;
import static com.xs.common.constants.SymbolConstants.TAB;

/**
 * 自定义匹配数据库表的注解@MatchTable
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
public @interface MatchTable {

    /**
     * 数据库表名
     */
    String tableName();

    class Utils extends AnnotationUtils {

        /**
         * 对类的属性进行赋值
         */
        public static void assign() throws RuntimeException {
            // 获取注解的Class对象
            Class<MatchTable> annotationClass = MatchTable.class;
            // 注解扫描路径
            String scanPath = getScanPath(annotationClass);
            List<String> errMsgList = new LinkedList<>();
            if (StringUtils.isEmpty(scanPath)) {
                String annotationName = annotationClass.getSimpleName();
                String errMsg = String.format(scanPathMissing_template, LINE_BREAK, TAB, annotationName, annotationName);
                throw new RuntimeException(errMsg);
            }
            List<Class<?>> classes = ClassUtils.getClasses(scanPath);
            if (classes != null && !classes.isEmpty()) {
                for (Class<?> clazz : classes) {
                    MatchTable annotation = clazz.getAnnotation(annotationClass);
                    if (annotation != null) {
                        String tableName = annotation.tableName();
                        // 校验类及其属性与数据库表的一致性
                        List<String> errMsgList1 = checkTable(clazz, tableName);
                        if (errMsgList1.isEmpty()) {
                            errMsgList.addAll(assign(clazz));
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
         * 对类的属性进行赋值
         *
         * @param clazz Class对象
         */
        private static List<String> assign(Class<?> clazz) {
            List<String> errMsgList = new LinkedList<>();
            try {
                Object obj = ClassUtils.newInstance(clazz);
                Field[] fields = obj.getClass().getFields();
                for (Field field : fields) {
                    Object value;
                    if (field.isAnnotationPresent(Value.class)) {
                        value = field.getAnnotation(Value.class).value();
                    } else {
                        value = field.getName();
                    }
                    XsUtils.setFieldValue(field, obj, value);
                }
            } catch (Exception e) {
                String errMsg = e.getMessage() + e.getCause();
                errMsgList.add(errMsg);
            }
            return errMsgList;
        }

    }

}
