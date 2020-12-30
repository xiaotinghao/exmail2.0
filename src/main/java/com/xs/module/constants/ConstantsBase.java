package com.xs.module.constants;

import com.xs.common.annotation.Assign;
import com.xs.common.annotation.Key;

/**
 * 基础常量类，类的属性与tableName表columnName字段的值对应
 *
 * @author 18871430207@163.com
 */
@Assign(tableSchema = "test", tableName = "t_constants_base", keyColumn = "constants_key", valueColumn = "constants_value")
public class ConstantsBase {

    @Key(keyName = "RESPONSE_EXPIRES_IN")
    public static String RESPONSE_EXPIRES_IN2;
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
