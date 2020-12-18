package com.xs.framework.service.impl;

import com.xs.framework.service.ApiCheckService;
import com.xs.framework.service.InterfaceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

/**
 * 应用程序接口校验接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class ApiCheckServiceImpl implements ApiCheckService {

    @Autowired
    InterfaceCheckService interfaceCheckService;

    @Override
    public boolean check(HandlerMethod handlerMethod) {
        return interfaceCheckService.check(handlerMethod);
    }

}
