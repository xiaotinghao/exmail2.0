package com.xs.module.constants;

import com.xs.common.annotation.ColumnCheck;
import com.xs.common.annotation.TableCheck;
import com.xs.common.annotation.ColumnCheckUtils;

/**
 * 常量类，与`t_input_interface_call_log`表字段对应
 *
 * @author 18871430207@163.com
 */
@TableCheck(tableName = "t_input_interface_call_log")
public class InterfaceCallLog {

    @ColumnCheck(columnName = "class_name")
    public static String class_name;
    @ColumnCheck(columnName = "method_name")
    public static String method_name;
    @ColumnCheck(columnName = "arg_types")
    public static String arg_types;
    @ColumnCheck(columnName = "arg_map")
    public static String arg_map;
    @ColumnCheck(columnName = "client_ip")
    public static String client_ip;
    @ColumnCheck(columnName = "client_host")
    public static String client_host;
    @ColumnCheck(columnName = "response_millis")
    public static String response_millis;

    static {
        ColumnCheckUtils.initFieldValue(InterfaceCallLog.class);
    }

}
