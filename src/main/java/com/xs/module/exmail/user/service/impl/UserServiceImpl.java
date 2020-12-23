package com.xs.module.exmail.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.constants.ConstantsConfig;
import com.xs.common.model.Result;
import com.xs.module.token.service.TokenService;
import com.xs.module.exmail.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.xs.common.constants.dynamic.ResultCodeMsg.*;

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
            String responseExpiresIn = ConstantsConfig.get("RESPONSE_EXPIRES_IN");
            jsonObject.put(responseExpiresIn, 300);
        } else {
            jsonObject = (JSONObject) JSONObject.toJSON(Result.get(ACCESS_TOKEN_OVERAGE));
        }
        return jsonObject;
    }

}
