package com.xs.common.interceptor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.constants.ResultCodeMsg;
import com.xs.common.dao.ConstantsDao;
import com.xs.common.entity.CodeMsg;
import com.xs.common.utils.http.HttpUtils;
import com.xs.module.exmail.log.dao.InterfaceLogDao;
import com.xs.common.interceptor.service.InterfaceCheckService;
import com.xs.module.exmail.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Map;

import static com.xs.module.constants.ExmailConstants.CALL_TOO_FREQUENTLY;
import static com.xs.module.constants.ExmailConstants.CORP_ID;

/**
 * 接口调用频率校验接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class InterfaceCheckServiceImpl implements InterfaceCheckService {

    @Autowired
    InterfaceLogDao interfaceLogDao;
    @Autowired
    ConstantsDao constantsDao;
    @Autowired
    TokenService tokenService;

    @Override
    public boolean corpValid(String corpId, String methodName) {
        String minuteLimit = constantsDao.getBaseConstants("CORP_API_CALL_MINUTE_UPPER_LIMIT");
        int minuteFrequency = interfaceLogDao.countCorpMinuteFrequency(corpId, methodName);
        if (Integer.valueOf(minuteLimit) < minuteFrequency) {
            return false;
        }
        String hourLimit = constantsDao.getBaseConstants("CORP_API_CALL_HOUR_UPPER_LIMIT");
        int hourFrequency = interfaceLogDao.countCorpHourFrequency(corpId, methodName);
        return Integer.valueOf(hourLimit) >= hourFrequency;
    }

    @Override
    public boolean ipValid(String ipAddress) {
        String minuteLimit = constantsDao.getBaseConstants("IP_API_CALL_MINUTE_UPPER_LIMIT");
        int minuteFrequency = interfaceLogDao.countIpMinuteFrequency(ipAddress);
        if (Integer.valueOf(minuteLimit) < minuteFrequency) {
            return false;
        }
        String hourLimit = constantsDao.getBaseConstants("IP_API_CALL_HOUR_UPPER_LIMIT");
        int hourFrequency = interfaceLogDao.countIpHourFrequency(ipAddress);
        return Integer.valueOf(hourLimit) >= hourFrequency;
    }

    @Override
    public boolean check(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        // 校验企业每ip调用接口频率
        String ipAddress = HttpUtils.getClientRealIp();
        if (!this.ipValid(ipAddress)) {
            Map<String, Object> objectMap = ResultCodeMsg.map.get("CALL_TOO_FREQUENTLY");
            HttpUtils.write(JSONObject.toJSONString(objectMap));
            return false;
        }
        // 获取请求参数
        Map<String, Object> map = HttpUtils.getRequestParam();
        if (map.containsKey(CORP_ID)) {
            String corpId = (String) map.get(CORP_ID);
            if (!this.corpValid(corpId, methodName)) {
                Map<String, Object> objectMap = ResultCodeMsg.map.get("CALL_TOO_FREQUENTLY");
                HttpUtils.write(JSONObject.toJSONString(objectMap));
                return false;
            }
        }
        return true;
    }
}
