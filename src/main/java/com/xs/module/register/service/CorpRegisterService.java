package com.xs.module.register.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 企业注册接口服务接口
 *
 * @author xiaotinghao
 */
@Repository
public interface CorpRegisterService {

    /**
     * 根据常量key获取常量
     *
     * @param corpId     企业id
     * @param corpSecret 应用的凭证密钥
     * @return 常量
     */
    boolean valid(@Param("corpId") String corpId, @Param("corpSecret") String corpSecret);

}