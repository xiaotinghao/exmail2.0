package com.xs.module.log.service;

import com.xs.framework.interceptor.model.HandleParam;

/**
 * 接口日志服务接口
 *
 * @author 18871430207@163.com
 */
public interface InterfaceLogService {

    /**
     * 保存接口请求日志
     *
     * @param handleParam 拦截器参数
     */
    void saveCallLog(HandleParam handleParam);

}