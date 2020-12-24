package com.xs.module.constants;

import com.xs.common.annotation.Column;
import com.xs.common.annotation.ConfiguredCheck;

/**
 * 基础常量类，类的属性与tableName表columnName字段的值对应
 *
 * @author 18871430207@163.com
 */
@ConfiguredCheck(tableName = "t_constants_base", columnName = "constants_key")
public class ConstantsBase {

    @Column(name = "constants_value")
    public static Long CORP_API_CALL_MINUTE_UPPER_LIMIT;
    public static Long CORP_API_CALL_HOUR_UPPER_LIMIT;
    public static Long IP_API_CALL_MINUTE_UPPER_LIMIT;
    public static Long IP_API_CALL_HOUR_UPPER_LIMIT;
    public static Integer MAP_INITIAL_CAPACITY;
    public static String CODE_KEY;
    public static String MSG_KEY;
    public static String DATA_KEY;
    public static String HANDLE_START_TIME;
    public static String REQUEST_CORP_ID;
    public static String RESPONSE_EXPIRES_IN;

}
