package com.xs.module.exmail.token.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Token服务接口
 *
 * @author 18871430207@163.com
 */
public interface TokenService {

    /**
     * 获取ACCESS_TOKEN
     *
     * @param corpId     企业id
     * @param corpSecret 应用的凭证密钥
     * @return ACCESS_TOKEN
     * access_token 获取到的凭证。长度为64至512个字节
     * expires_in 凭证的有效时间（秒）
     */
    JSONObject getToken(String corpId, String corpSecret);

    /**
     * 通过调用接口凭证解析
     * 企业id和应用的凭证密钥
     *
     * @param accessToken 调用接口凭证
     * @return Array
     * corpId = array[0]
     * corpSecret = array[1]
     * currentTimeMillis = array[2]
     */
    String[] parseToken(String accessToken);

    /**
     * 检验ACCESS_TOKEN有效性
     *
     * @param accessToken 调用接口凭证
     * @return true-有效，false-失效
     */
    boolean validate(String accessToken);

}
