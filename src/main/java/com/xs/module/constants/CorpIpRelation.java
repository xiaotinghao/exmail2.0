package com.xs.module.constants;

import com.xs.common.annotation.Column;
import com.xs.common.annotation.Table;

/**
 * 常量类，与`t_input_corp_ip_relation`表字段对应
 *
 * @author 18871430207@163.com
 */
@Table(tableName = "t_input_corp_ip_relation")
public class CorpIpRelation {

    public static String corp_id;
    @Column(columnName = "client_ip")
    public static String client_ip;

}
