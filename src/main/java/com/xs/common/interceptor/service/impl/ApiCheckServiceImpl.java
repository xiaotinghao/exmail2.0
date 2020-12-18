package com.xs.common.interceptor.service.impl;

import com.xs.common.interceptor.service.ApiCheckService;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

/**
 * 应用程序接口校验接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class ApiCheckServiceImpl extends InterfaceCheckServiceImpl implements ApiCheckService {

    @Override
    public boolean check(HandlerMethod handlerMethod) {
        return super.check(handlerMethod);
    }

}
