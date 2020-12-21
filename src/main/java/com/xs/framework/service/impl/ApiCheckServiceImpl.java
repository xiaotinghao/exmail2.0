package com.xs.framework.service.impl;

import com.xs.framework.service.ApiCheckService;
import com.xs.framework.service.InterfaceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public boolean check(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return interfaceCheckService.check(request, response, handler);
    }

}
