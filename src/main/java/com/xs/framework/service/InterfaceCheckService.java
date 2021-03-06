package com.xs.framework.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口调用频率校验接口
 *
 * @author 18871430207@163.com
 */
public interface InterfaceCheckService {

    /**
     * 每企业调用单个cgi/api不可超过500次/分，15000次/小时
     *
     * @param corpId     企业id
     * @param methodName 方法名称
     * @return 校验结果
     */
    boolean corpValid(String corpId, String methodName);

    /**
     * 企业每ip调用接口不可超过10000次/分，300000次/小时
     *
     * @param ipAddress 客户端请求的ip地址
     * @return 校验结果
     */
    boolean ipValid(String ipAddress);

    /**
     * 接口校验
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  拦截器对象
     * @return 校验结果
     */
    boolean check(HttpServletRequest request, HttpServletResponse response, Object handler);

}