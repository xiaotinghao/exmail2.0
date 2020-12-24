package com.xs.module.token;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.spring.SpringTool;

import static com.xs.common.constants.SymbolConstants.SEPARATOR;
import static com.xs.module.constants.ConstantsToken.TOKEN_ENCRYPTION_KEY;

/**
 * Token算法
 *
 * @author 18871430207@163.com
 */
public class TokenAlgorithm {

    private static BaseDao baseDao;

    static {
        if (baseDao == null) {
            baseDao = SpringTool.getBean(BaseDao.class);
        }
    }

    /**
     * token拼接规则
     *
     * @param arr 待拼接数组
     * @return 拼接结果 "run-._app00001-._1607572040317"
     */
    public static String join(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (String str : arr) {
            sb.append(SEPARATOR).append(str);
        }
        return sb.substring(SEPARATOR.length());
    }

    /**
     * token拆分规则，与token拼接规则对应
     *
     * @param str 待拆分字符串
     * @return 拆分后的数组 {"run","app00001","1607572040317"}
     */
    public static String[] split(String str) {
        String[] result = new String[0];
        if (str == null || !str.contains(SEPARATOR)) {
            return result;
        }
        return str.split(SEPARATOR);
    }

    /**
     * 使用pwd作为密码，加密字符串encode
     *
     * @param encode 待加密字符串
     * @return 加密后的字符decode
     */
    public static String encrypt(String encode) {
        return baseDao.encode(encode, TOKEN_ENCRYPTION_KEY);
    }

    /**
     * 使用pwd作为密码，解密字符串decode
     *
     * @param decode 待解密字符串
     * @return 解密获得的字符encode
     */
    public static String decrypt(String decode) {
        return baseDao.decode(decode, TOKEN_ENCRYPTION_KEY);
    }

}
