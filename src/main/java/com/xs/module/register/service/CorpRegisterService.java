package com.xs.module.register.service;

/**
 * 企业注册接口服务接口
 *
 * @author 18871430207@163.com
 */
public interface CorpRegisterService {

    /**
     * 校验corpId和corpSecret是否注册
     *
     * @param corpId     企业id
     * @param corpSecret 应用的凭证密钥
     * @return 校验结果
     */
    boolean valid(String corpId, String corpSecret);

}