package com.xs.module.exmail.token.constants;

import static com.xs.common.constants.SymbolConstants.SEPARATOR;

/**
 * Token常量类（与t_constants_token表中的constants_key保持一致）
 *
 * @author xiaotinghao
 */
public class TokenConstants {

    /**
     * token模块名称【查询key】
     */
    public static final String MODULE_TOKEN = "MODULE_TOKEN";

    /**
     * token加密密钥【查询key】
     */
    public static final String TOKEN_ENCRYPTION_KEY = "TOKEN_ENCRYPTION_KEY";

    /**
     * token时效（单位毫秒）【查询key】
     */
    public static final String TOKEN_TIME_EFFICIENT = "TOKEN_TIME_EFFICIENT";

    /**
     * ACCESS_TOKEN 待加密字符串样例
     */
    public static final String ACCESS_TOKEN_DEMO = "run" + SEPARATOR + "app00001" + SEPARATOR + "1607572040317";


}
