package com.xs.module.constants;

import com.xs.common.annotation.Column;
import com.xs.common.annotation.Table;

/**
 * 常量类，与`t_input_interface_call_log`表字段对应
 *
 * @author 18871430207@163.com
 */
@Table(tableSchema = "test", tableName = "t_input_interface_call_log")
public class InterfaceCallLog {

    public static String class_name;
    public static String method_name;
    @Column(columnName = "arg_types")
    public static String argTypes;
    public static String arg_map;
    public static String client_ip;
    public static String client_host;
    public static String response_millis;

}
