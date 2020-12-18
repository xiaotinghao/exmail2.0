package com.xs.framework.service.impl;

import com.xs.common.constants.ConstantsConfig;
import com.xs.common.model.Result;
import com.xs.common.utils.http.HttpUtils;
import com.xs.module.exmail.log.dao.InterfaceLogDao;
import com.xs.framework.service.InterfaceCheckService;
import com.xs.module.exmail.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Map;

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
    TokenService tokenService;

    @Override
    public boolean corpValid(String corpId, String methodName) {
        String minuteLimit = ConstantsConfig.get("CORP_API_CALL_MINUTE_UPPER_LIMIT", "50");
        int minuteFrequency = interfaceLogDao.countCorpMinuteFrequency(corpId, methodName);
        if (Integer.valueOf(minuteLimit) < minuteFrequency) {
            return false;
        }
        String hourLimit = ConstantsConfig.get("CORP_API_CALL_HOUR_UPPER_LIMIT", "1000");
        int hourFrequency = interfaceLogDao.countCorpHourFrequency(corpId, methodName);
        return Integer.valueOf(hourLimit) >= hourFrequency;
    }

    @Override
    public boolean ipValid(String ipAddress) {
        String minuteLimit = ConstantsConfig.get("IP_API_CALL_MINUTE_UPPER_LIMIT", "500");
        int minuteFrequency = interfaceLogDao.countIpMinuteFrequency(ipAddress);
        if (Integer.valueOf(minuteLimit) < minuteFrequency) {
            return false;
        }
        String hourLimit = ConstantsConfig.get("IP_API_CALL_HOUR_UPPER_LIMIT", "10000");
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
            HttpUtils.sendError(400, Result.get("CALL_TOO_FREQUENTLY"));
            return false;
        }
        // 获取请求参数
        Map<String, Object> map = HttpUtils.getRequestParam();
        if (map.containsKey(CORP_ID)) {
            String corpId = (String) map.get(CORP_ID);
            if (!this.corpValid(corpId, methodName)) {
                HttpUtils.sendError(400, Result.get("CALL_TOO_FREQUENTLY"));
                return false;
            }
        }
        return true;
    }
}
