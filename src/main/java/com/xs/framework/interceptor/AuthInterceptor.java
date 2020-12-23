package com.xs.framework.interceptor;

import com.xs.common.constants.ConstantsConfig;
import com.xs.common.model.Result;
import com.xs.common.utils.http.HttpUtils;
import com.xs.module.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.xs.common.constants.dynamic.ResultCodeMsg.*;

/**
 * 权限拦截器
 *
 * @author 18871430207@163.com
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求参数
        Map<String, Object> map = HttpUtils.getRequestParam(request);
        String accessTokenVariableName = ConstantsConfig.get("REQUEST_ACCESS_TOKEN");
        if (map.containsKey(accessTokenVariableName)) {
            String accessToken = (String) map.get(accessTokenVariableName);
            if (tokenService.validate(accessToken)) {
                return true;
            } else {
                response.sendError(401, Result.get(INVALID_ACCESS_TOKEN));
                return false;
            }
        }
        response.sendError(401, Result.get(ACCESS_TOKEN_MISSING));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}