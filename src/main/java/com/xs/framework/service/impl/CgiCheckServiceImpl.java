package com.xs.framework.service.impl;

import com.xs.common.model.Result;
import com.xs.common.utils.http.HttpUtils;
import com.xs.framework.service.CgiCheckService;
import com.xs.framework.service.InterfaceCheckService;
import com.xs.module.exmail.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Map;

import static com.xs.module.constants.ExmailConstants.*;

/**
 * 通用网关接口校验接口
 *
 * @author 18871430207@163.com
 */
@Service
public class CgiCheckServiceImpl implements CgiCheckService {

    @Autowired
    InterfaceCheckService interfaceCheckService;
    @Autowired
    TokenService tokenService;

    @Override
    public boolean check(HandlerMethod handlerMethod) {
        boolean superCheck = interfaceCheckService.check(handlerMethod);
        if (superCheck) {
            Method method = handlerMethod.getMethod();
            String methodName = method.getName();
            // 获取请求参数
            Map<String, Object> map = HttpUtils.getRequestParam();
            if (map.containsKey(ACCESS_TOKEN)) {
                String accessToken = (String) map.get(ACCESS_TOKEN);
                if (tokenService.validate(accessToken)) {
                    String[] splits = tokenService.parseToken(accessToken);
                    String corpId = splits[0];
                    if (!interfaceCheckService.corpValid(corpId, methodName)) {
                        HttpUtils.sendError(400, Result.get("CALL_TOO_FREQUENTLY"));
                        return false;
                    }
                } else {
                    HttpUtils.sendError(400, Result.get("INVALID_ACCESS_TOKEN"));
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
