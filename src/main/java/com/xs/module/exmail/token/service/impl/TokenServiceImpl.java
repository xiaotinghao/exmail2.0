package com.xs.module.exmail.token.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.dao.BaseDao;
import com.xs.common.dao.ConstantsDao;
import com.xs.common.entity.CodeMsg;
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
 * @author xiaotinghao
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
            CodeMsg codeMsg = constantsDao.getCodeMsg(INVALID_CREDENTIAL);
            return (JSONObject) JSONObject.toJSON(codeMsg);
        }

        CodeMsg codeMsg = constantsDao.getCodeMsg(SUCCESS_CODE_MESSAGE);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(codeMsg);

        long millis = System.currentTimeMillis();
        // 待加密字符串
        String str = corpId + SEPARATOR + corpSecret + SEPARATOR + millis;
        // 加密生成accessToken
        String moduleToken = constantsDao.getBaseConstants(MODULE_TOKEN);
        String pwd = constantsDao.getModuleConstants(TOKEN_ENCRYPTION_KEY, moduleToken);
        String accessToken = baseDao.encode(str, pwd);
        jsonObject.put(access_token, accessToken);

        // 查询token时效（单位毫秒）
        String timeEfficient = constantsDao.getModuleConstants(TOKEN_TIME_EFFICIENT, moduleToken);
        jsonObject.put(expires_in, Long.valueOf(timeEfficient));
        return jsonObject;
    }

    @Override
    public boolean validate(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) {
            return false;
        }
        String moduleToken = constantsDao.getBaseConstants(MODULE_TOKEN);
        String pwd = constantsDao.getModuleConstants(TOKEN_ENCRYPTION_KEY, moduleToken);
        String str = baseDao.decode(accessToken, pwd);
        if (!str.contains(SEPARATOR)) {
            return false;
        }
        String[] splits = str.split(SEPARATOR);
        // ACCESS_TOKEN_DEMO = "run-._app00001-._1607572040317"
        if (splits.length != ACCESS_TOKEN_DEMO.split(SEPARATOR).length) {
            return false;
        }
        Long t1;
        try {
            // splits格式：{"run","app00001","1607572040317"}
            String corpId = splits[0];
            String corpSecret = splits[1];
            // 校验corpId和corpSecret是否注册
            boolean valid = corpRegisterService.valid(corpId, corpSecret);
            if (!valid) {
                return false;
            }
            // 初始获取token的时间戳
            t1 = Long.valueOf(splits[2]);
        } catch (Exception e) {
            return false;
        }
        // 当前时间戳
        long t2 = System.currentTimeMillis();
        // 查询token时效（单位毫秒）
        String timeEfficient = constantsDao.getModuleConstants(TOKEN_TIME_EFFICIENT, moduleToken);
        return t2 - t1 <= Long.valueOf(timeEfficient);
    }

}
