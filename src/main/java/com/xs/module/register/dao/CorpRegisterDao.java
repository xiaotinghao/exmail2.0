package com.xs.module.register.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 企业注册数据服务层
 *
 * @author xiaotinghao
 */
@Repository
public interface CorpRegisterDao {

    /**
     * 根据常量key获取常量
     *
     * @param corpId     企业id
     * @param corpSecret 应用的凭证密钥
     * @return 常量
     */
    int valid(@Param("corpId") String corpId, @Param("corpSecret") String corpSecret);

}