package com.xs.common.interceptor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.constants.ResultCodeMsg;
import com.xs.common.entity.CodeMsg;
import com.xs.common.utils.http.HttpUtils;
import com.xs.common.interceptor.service.CgiCheckService;
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
public class CgiCheckServiceImpl extends InterfaceCheckServiceImpl implements CgiCheckService {

    @Autowired
    TokenService tokenService;

    @Override
    public boolean check(HandlerMethod handlerMethod) {
        boolean superCheck = super.check(handlerMethod);
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
                    if (!super.corpValid(corpId, methodName)) {
                        Map<String, Object> objectMap = ResultCodeMsg.map.get("CALL_TOO_FREQUENTLY");
                        HttpUtils.write(JSONObject.toJSONString(objectMap));
                        return false;
                    }
                } else {
                    Map<String, Object> objectMap = ResultCodeMsg.map.get("INVALID_ACCESS_TOKEN");
                    HttpUtils.write(JSONObject.toJSONString(objectMap));
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
