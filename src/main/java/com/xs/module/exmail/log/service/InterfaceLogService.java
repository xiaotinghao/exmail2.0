package com.xs.module.exmail.log.service;

/**
 * 接口日志服务接口
 *
 * @author 18871430207@163.com
 */
public interface InterfaceLogService {

    /**
     * 保存日志
     *
     * @param className      类名
     * @param methodName     方法名
     * @param args           参数值
     * @param parameterNames 参数名称
     */
    void saveLog(String className, String methodName, Object[] args, String[] parameterNames);

}