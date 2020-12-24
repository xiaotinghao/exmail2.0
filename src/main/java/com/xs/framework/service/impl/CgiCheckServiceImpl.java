package com.xs.framework.service.impl;

import com.xs.common.utils.StringUtils;
import com.xs.common.utils.XsUtils;
import com.xs.common.utils.http.HttpUtils;
import com.xs.framework.service.CgiCheckService;
import com.xs.framework.service.InterfaceCheckService;
import com.xs.module.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

import static com.xs.module.constants.ConstantsToken.REQUEST_ACCESS_TOKEN;
import static com.xs.module.constants.ResultCodeMsg.CALL_TOO_FREQUENTLY;
import static com.xs.module.constants.ResultCodeMsg.INVALID_ACCESS_TOKEN;

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
    public boolean check(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean superCheck = interfaceCheckService.check(request, response, handler);
        if (superCheck) {
            HandlerMethod handlerMethod = XsUtils.cast(handler);
            Method method = handlerMethod.getMethod();
            String methodName = method.getName();
            // 获取请求参数
            Map<String, Object> map = HttpUtils.getRequestParam(request);
            if (map.containsKey(REQUEST_ACCESS_TOKEN)) {
                String accessToken = (String) map.get(REQUEST_ACCESS_TOKEN);
                if (tokenService.validate(accessToken)) {
                    String corpId = tokenService.getCorpId(accessToken);
                    if (StringUtils.isNotEmpty(corpId)) {
                        if (!interfaceCheckService.corpValid(corpId, methodName)) {
                            HttpUtils.sendError(400, CALL_TOO_FREQUENTLY.msg);
                            return false;
                        }
                    } else {
                        HttpUtils.sendError(400, INVALID_ACCESS_TOKEN.msg);
                        return false;
                    }
                } else {
                    HttpUtils.sendError(400, INVALID_ACCESS_TOKEN.msg);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
