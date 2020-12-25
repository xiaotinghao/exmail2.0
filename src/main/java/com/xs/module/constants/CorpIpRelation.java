package com.xs.module.constants;

import com.xs.common.annotation.MatchTable;
import org.springframework.beans.factory.annotation.Value;

/**
 * 常量类，与`t_input_corp_ip_relation`表字段对应
 *
 * @author 18871430207@163.com
 */
@MatchTable(tableName = "t_input_corp_ip_relation")
public class CorpIpRelation {

    public static String corp_id;
    @Value("client_ip")
    public static String client_ip;

}
