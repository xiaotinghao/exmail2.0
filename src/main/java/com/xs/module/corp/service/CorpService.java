package com.xs.module.corp.service;

import com.xs.common.service.BaseService;
import com.xs.framework.interceptor.model.HandleParam;

/**
 * 企业相关业务接口
 *
 * @author 18871430207@163.com
 */
public interface CorpService extends BaseService {

    /**
     * 保存企业id和客户端ip的匹配关系
     *
     * @param handleParam 拦截器参数
     */
    void saveCorpIpRelation(HandleParam handleParam);

}
