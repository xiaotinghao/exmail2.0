package com.xs.module.constants;

import com.xs.common.annotation.ClassFieldAssign;

/**
 * 测试常量类，类的属性与tableName表columnName字段的值对应
 *
 * @author 18871430207@163.com
 */
@ClassFieldAssign(tableName = "t_result_code_msg", columnName = "key")
public class ResultCodeMsg {

    public static class CodeMsg {
        public Integer code11;
        public String msg;
    }

    public static CodeMsg SUCCESS_CODE_MESSAGE;
    public static CodeMsg ERROR_CODE_MESSAGE;
    public static CodeMsg CALL_TOO_FREQUENTLY;
    public static CodeMsg INVALID_ACCESS_TOKEN;
    public static CodeMsg ACCESS_TOKEN_OVERAGE;
    public static CodeMsg ACCESS_TOKEN_MISSING;
    public static CodeMsg INVALID_CREDENTIAL;

}
