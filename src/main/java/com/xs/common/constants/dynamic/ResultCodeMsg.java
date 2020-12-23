package com.xs.common.constants.dynamic;

import com.xs.common.annotation.TableCheck;
import com.xs.common.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * 结果集常量类，动态获取`t_result_code_msg`表数据
 *
 * @author 18871430207@163.com
 */
@TableCheck(tableName = "t_result_code_msg")
public class ResultCodeMsg {

    public static String SUCCESS_CODE_MESSAGE;
    public static String ERROR_CODE_MESSAGE;
    public static String CALL_TOO_FREQUENTLY;
    public static String INVALID_ACCESS_TOKEN;
    public static String ACCESS_TOKEN_OVERAGE;
    public static String ACCESS_TOKEN_MISSING;
    public static String INVALID_CREDENTIAL;

    static {
        Object obj = ClassUtils.newInstance(ResultCodeMsg.class);
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            try {
                field.set(obj, field.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
