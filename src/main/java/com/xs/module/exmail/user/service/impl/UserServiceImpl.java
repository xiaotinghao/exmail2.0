package com.xs.module.exmail.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.constants.ResultCodeMsg;
import com.xs.common.dao.ConstantsDao;
import com.xs.common.entity.CodeMsg;
import com.xs.module.exmail.token.service.TokenService;
import com.xs.module.exmail.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.xs.module.constants.ExmailConstants.ACCESS_TOKEN_OVERAGE;

/**
 * Token服务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    TokenService tokenService;
    @Autowired
    ConstantsDao constantsDao;

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
            jsonObject.put("expires_in", 300);
        } else {
            Map<String, Object> objectMap = ResultCodeMsg.map.get("ACCESS_TOKEN_OVERAGE");
            jsonObject = (JSONObject) JSONObject.toJSON(objectMap);
        }
        return jsonObject;
    }

}
