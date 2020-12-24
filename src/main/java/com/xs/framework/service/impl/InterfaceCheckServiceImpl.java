package com.xs.framework.service.impl;

import com.xs.common.utils.XsUtils;
import com.xs.common.utils.http.HttpUtils;
import com.xs.module.log.dao.InterfaceLogDao;
import com.xs.framework.service.InterfaceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

import static com.xs.module.constants.ConstantsBase.*;
import static com.xs.module.constants.ResultCodeMsg.CALL_TOO_FREQUENTLY;

/**
 * 接口调用频率校验接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class InterfaceCheckServiceImpl implements InterfaceCheckService {

    @Autowired
    InterfaceLogDao interfaceLogDao;

    @Override
    public boolean corpValid(String corpId, String methodName) {
        long minuteFrequency = interfaceLogDao.countCorpMinuteFrequency(corpId, methodName);
        if (CORP_API_CALL_MINUTE_UPPER_LIMIT < minuteFrequency) {
            return false;
        }
        long hourFrequency = interfaceLogDao.countCorpHourFrequency(corpId, methodName);
        return CORP_API_CALL_HOUR_UPPER_LIMIT >= hourFrequency;
    }

    @Override
    public boolean ipValid(String ipAddress) {
        long minuteFrequency = interfaceLogDao.countIpMinuteFrequency(ipAddress);
        if (IP_API_CALL_MINUTE_UPPER_LIMIT < minuteFrequency) {
            return false;
        }
        long hourFrequency = interfaceLogDao.countIpHourFrequency(ipAddress);
        return IP_API_CALL_HOUR_UPPER_LIMIT >= hourFrequency;
    }

    @Override
    public boolean check(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = XsUtils.cast(handler);
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        // 校验企业每ip调用接口频率
        String ipAddress = HttpUtils.getClientRealIp(request);
        if (!this.ipValid(ipAddress)) {
            HttpUtils.sendError(400, CALL_TOO_FREQUENTLY.msg);
            return false;
        }
        // 获取请求参数
        Map<String, Object> map = HttpUtils.getRequestParam(request);
        if (map.containsKey(REQUEST_CORP_ID)) {
            String corpId = (String) map.get(REQUEST_CORP_ID);
            if (!this.corpValid(corpId, methodName)) {
                HttpUtils.sendError(400, CALL_TOO_FREQUENTLY.msg);
                return false;
            }
        }
        return true;
    }
}
