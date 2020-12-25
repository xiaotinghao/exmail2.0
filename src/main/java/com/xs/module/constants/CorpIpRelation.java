package com.xs.module.constants;

import com.xs.common.annotation.ColumnCheck;
import com.xs.common.annotation.TableCheck;

/**
 * 常量类，与`t_input_corp_ip_relation`表字段对应
 *
 * @author 18871430207@163.com
 */
@TableCheck(tableName = "t_input_corp_ip_relation")
public class CorpIpRelation {

    @ColumnCheck(columnName = "corp_id1")
    public static String corp_id;
    @ColumnCheck(columnName = "client_ip")
    public static String client_ip;

    static {
        ColumnCheck.Utils.initFieldValue(CorpIpRelation.class);
    }

}
