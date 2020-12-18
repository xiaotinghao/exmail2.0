package com.xs.module.exmail.token.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.constants.ConstantsConfig;
import com.xs.common.dao.BaseDao;
import com.xs.common.dao.ConstantsDao;
import com.xs.common.model.Result;
import com.xs.common.utils.StringUtils;
import com.xs.module.exmail.token.service.TokenService;
import com.xs.module.register.service.CorpRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.xs.common.constants.SymbolConstants.*;
import static com.xs.module.constants.ExmailConstants.*;
import static com.xs.module.exmail.token.constants.TokenConstants.*;

/**
 * Token服务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    BaseDao baseDao;
    @Autowired
    ConstantsDao constantsDao;
    @Autowired
    CorpRegisterService corpRegisterService;

    @Override
    public JSONObject getToken(String corpId, String corpSecret) {
        // 校验corpId和corpSecret是否注册
        boolean valid = corpRegisterService.valid(corpId, corpSecret);
        if (!valid) {
            return (JSONObject) JSONObject.toJSON(Result.get("INVALID_CREDENTIAL"));
        }

        JSONObject jsonObject = JSONObject.parseObject(Result.success());

        long millis = System.currentTimeMillis();
        // 待加密字符串
        String str = corpId + SEPARATOR + corpSecret + SEPARATOR + millis;
        // 加密生成accessToken
        String pwd = ConstantsConfig.get("TOKEN_ENCRYPTION_KEY", "xxx");
        String accessToken = baseDao.encode(str, pwd);
        jsonObject.put(access_token, accessToken);

        // 查询token时效（单位毫秒）
        String timeEfficient = ConstantsConfig.get("TOKEN_TIME_EFFICIENT", "7200000");
        jsonObject.put(expires_in, Long.valueOf(timeEfficient));
        return jsonObject;
    }

    @Override
    public String[] parseToken(String accessToken) {
        String[] result = new String[0];
        if (StringUtils.isEmpty(accessToken)) {
            return result;
        }
        String pwd = ConstantsConfig.get("TOKEN_ENCRYPTION_KEY", "xxx");
        String str = baseDao.decode(accessToken, pwd);
        if (str == null || !str.contains(SEPARATOR)) {
            return result;
        }
        String[] splits = str.split(SEPARATOR);
        // ACCESS_TOKEN_DEMO = "run-._app00001-._1607572040317"
        if (splits.length != ACCESS_TOKEN_DEMO.split(SEPARATOR).length) {
            return result;
        }
        try {
            // splits格式：{"run","app00001","1607572040317"}
            String corpId = splits[0];
            String corpSecret = splits[1];
            // 校验corpId和corpSecret是否注册
            boolean valid = corpRegisterService.valid(corpId, corpSecret);
            if (!valid) {
                return result;
            }
        } catch (Exception e) {
            return result;
        }
        return splits;
    }

    @Override
    public boolean validate(String accessToken) {
        String[] splits = this.parseToken(accessToken);
        if (splits.length == 0) {
            return false;
        }
        Long t1;
        try {
            t1 = Long.valueOf(splits[2]);
        } catch (Exception e) {
            return false;
        }
        // 当前时间戳
        long t2 = System.currentTimeMillis();
        // 查询token时效（单位毫秒）
        String timeEfficient = ConstantsConfig.get("TOKEN_TIME_EFFICIENT", "7200000");
        return t2 - t1 <= Long.valueOf(timeEfficient);
    }

}
