package com.xs.framework.interceptor;

import com.xs.common.utils.http.HttpUtils;
import com.xs.module.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.xs.module.constants.ConstantsToken.REQUEST_ACCESS_TOKEN;
import static com.xs.module.constants.ResultCodeMsg.ACCESS_TOKEN_MISSING;
import static com.xs.module.constants.ResultCodeMsg.INVALID_ACCESS_TOKEN;

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
        if (map.containsKey(REQUEST_ACCESS_TOKEN)) {
            String accessToken = (String) map.get(REQUEST_ACCESS_TOKEN);
            if (tokenService.validate(accessToken)) {
                return true;
            } else {
                response.sendError(401, INVALID_ACCESS_TOKEN.msg);
                return false;
            }
        }
        response.sendError(401, ACCESS_TOKEN_MISSING.msg);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}