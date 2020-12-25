package com.xs.module.constants;

import com.xs.common.annotation.ClassFieldAssign;

/**
 * token模块常量类，类的属性与tableName表columnName字段的值对应
 *
 * @author 18871430207@163.com
 */
@ClassFieldAssign(tableName = "t_constants_token")
public class ConstantsToken {

    public static Long TOKEN_TIME_EFFICIENT;
    public static String TOKEN_ENCRYPTION_KEY;
    public static String REQUEST_ACCESS_TOKEN;
    public static String RESPONSE_ACCESS_TOKEN;

}
