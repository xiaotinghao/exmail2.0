package com.xs.module.log.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Api调用日志数据服务层
 *
 * @author 18871430207@163.com
 */
@Repository
public interface InterfaceLogDao {

    /**
     * 保存接口调用日志
     *
     * @param objectMap 日志数据
     */
    void saveCallLog(Map<String, Object> objectMap);

    /**
     * 获取企业单个api接口每分钟调用频率
     *
     * @param corpId     企业id
     * @param methodName api接口名称
     * @return 每分钟调用频率
     */
    long countCorpMinuteFrequency(@Param("corpId") String corpId, @Param("methodName") String methodName);

    /**
     * 获取企业单个api接口每小时调用频率
     *
     * @param corpId     企业id
     * @param methodName api接口名称
     * @return 每小时调用频率
     */
    long countCorpHourFrequency(@Param("corpId") String corpId, @Param("methodName") String methodName);

    /**
     * 获取企业单个ip每分钟调用接口频率
     *
     * @param clientIp 客户端id
     * @return 每分钟调用频率
     */
    long countIpMinuteFrequency(@Param("clientIp") String clientIp);

    /**
     * 获取企业单个ip每小时调用接口频率
     *
     * @param clientIp 客户端id
     * @return 每小时调用频率
     */
    long countIpHourFrequency(@Param("clientIp") String clientIp);

}