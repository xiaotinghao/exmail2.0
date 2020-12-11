package com.xs.module.exmail.token;

/**
 * Token算法
 *
 * @author xiaotinghao
 */
public class TokenAlgorithm {

    /**
     * 通过corpId和corpSecret计算access_token
     *
     * @param corpId     企业id
     * @param corpSecret 应用的凭证密钥
     */
    public static String formula(String corpId, String corpSecret) {
        long millis = System.currentTimeMillis();
        return corpId + corpSecret + millis;
    }

    /**
     * 检验ACCESS_TOKEN有效性
     *
     * @param accessToken 调用接口凭证
     * @return true-有效，false-失效
     */
    public static boolean validate(String accessToken) {
        long millis = System.currentTimeMillis();
        return false;
    }

}
