package com.xs.module.token.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xs.common.model.Result;
import com.xs.module.token.TokenAlgorithm;
import com.xs.module.token.service.TokenService;
import com.xs.module.register.service.CorpRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.xs.module.constants.ConstantsBase.RESPONSE_EXPIRES_IN;
import static com.xs.module.constants.ConstantsToken.RESPONSE_ACCESS_TOKEN;
import static com.xs.module.constants.ConstantsToken.TOKEN_TIME_EFFICIENT;
import static com.xs.module.constants.ResultCodeMsg.INVALID_CREDENTIAL;

/**
 * Token服务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    CorpRegisterService corpRegisterService;

    @Override
    public JSONObject getToken(String corpId, String corpSecret) {
        // 校验corpId和corpSecret是否注册
        boolean valid = corpRegisterService.valid(corpId, corpSecret);
        if (!valid) {
            return (JSONObject) JSON.toJSON(INVALID_CREDENTIAL);
        }
        JSONObject jsonObject = JSON.parseObject(Result.success());
        // 当前时间毫秒数
        long millis = System.currentTimeMillis();
        // 待拼接数组
        String[] arr = {corpId, corpSecret, String.valueOf(millis)};
        // 获取待加密字符串
        String encode = TokenAlgorithm.join(arr);
        // 加密生成accessToken
        String accessToken = TokenAlgorithm.encrypt(encode);
        jsonObject.put(RESPONSE_ACCESS_TOKEN, accessToken);
        // 查询token时效配置（单位毫秒）
        jsonObject.put(RESPONSE_EXPIRES_IN, TOKEN_TIME_EFFICIENT);
        return jsonObject;
    }

    @Override
    public String getCorpId(String accessToken) {
        if (validate(accessToken)) {
            // 解密accessToken
            String encode = TokenAlgorithm.decrypt(accessToken);
            // 获取拼接数组
            String[] splits = TokenAlgorithm.split(encode);
            if (splits.length > 0) {
                return splits[0];
            }
        }
        return "";
    }

    @Override
    public boolean validate(String accessToken) {
        // 解密accessToken
        String encode = TokenAlgorithm.decrypt(accessToken);
        // 获取拼接数组
        String[] arr = TokenAlgorithm.split(encode);
        if (arr.length == 0) {
            return false;
        }
        Long t1;
        try {
            // arr格式：{"run","app00001","1607572040317"}
            String corpId = arr[0];
            String corpSecret = arr[1];
            t1 = Long.valueOf(arr[2]);
            // 校验corpId和corpSecret是否注册
            boolean valid = corpRegisterService.valid(corpId, corpSecret);
            if (!valid) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        // 当前时间戳
        long t2 = System.currentTimeMillis();
        // 查询token时效（单位毫秒）
        return t2 - t1 <= TOKEN_TIME_EFFICIENT;
    }

}
