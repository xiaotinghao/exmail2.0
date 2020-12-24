package com.xs.module.exmail.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xs.module.token.service.TokenService;
import com.xs.module.exmail.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.xs.module.constants.ConstantsBase.RESPONSE_EXPIRES_IN;
import static com.xs.module.constants.ResultCodeMsg.ACCESS_TOKEN_OVERAGE;

/**
 * Token服务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    TokenService tokenService;

    @Override
    public JSONObject getLoginUrl(String accessToken, String userId) {
        // 检验accessToken有效性
        boolean validity = tokenService.validate(accessToken);
        JSONObject jsonObject = new JSONObject();
        if (validity) {
            jsonObject.put("errcode", 0);
            jsonObject.put("errmsg", "ok");
            jsonObject.put("login_url", "https://exmail.qq.com/cgi-bin/login" +
                    "?fun=bizopenssologin&method=openapi&userid=zhangsanp@gzdev.com&authkey=XXXX");
            jsonObject.put(RESPONSE_EXPIRES_IN, 300);
        } else {
            jsonObject = (JSONObject) JSONObject.toJSON(ACCESS_TOKEN_OVERAGE);
        }
        return jsonObject;
    }

}
