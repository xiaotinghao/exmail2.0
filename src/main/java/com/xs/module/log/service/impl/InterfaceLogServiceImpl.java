package com.xs.module.log.service.impl;

import com.xs.common.utils.ArrayUtils;
import com.xs.common.utils.MapUtils;
import com.xs.common.utils.http.HttpUtils;
import com.xs.framework.interceptor.model.HandleParam;
import com.xs.module.log.dao.InterfaceLogDao;
import com.xs.module.log.service.InterfaceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

import static com.xs.common.constants.WebConstants.HANDLE_BEAN_NAME_SEPARATOR;
import static com.xs.module.constants.ConstantsBase.HANDLE_START_TIME;
import static com.xs.module.constants.InterfaceCallLog.*;

/**
 * 接口日志服务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class InterfaceLogServiceImpl implements InterfaceLogService {

    @Autowired
    InterfaceLogDao interfaceLogDao;

    @Override
    public void saveCallLog(HandleParam handleParam) {
        // 日志数据Map
        Map<String, Object> objectMap = MapUtils.init();
        // 获取HttpServletRequest
        HttpServletRequest request = handleParam.getRequest();
        HandlerMethod handlerMethod = (HandlerMethod) handleParam.getHandler();
        // 获取请求类名称
        String className = handlerMethod.getBean().getClass().getName();
        if (className != null && className.contains(HANDLE_BEAN_NAME_SEPARATOR)) {
            className = className.substring(0, className.indexOf(HANDLE_BEAN_NAME_SEPARATOR));
        }
        objectMap.put(class_name, className);
        Method method = handlerMethod.getMethod();
        // 获取请求方法名称
        String methodName = handlerMethod.getMethod().getName();
        objectMap.put(method_name, methodName);
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] argTypes = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            argTypes[i] = parameterTypes[i].getSimpleName();
        }
        // 获取请求参数类型
        objectMap.put(arg_types, ArrayUtils.toString(argTypes));
        // 获取请求参数
        Map<String, Object> map = HttpUtils.getRequestParam(request);
        objectMap.put(arg_map, map.toString());
        // 获取客户端ip
        String ipAddress = HttpUtils.getClientRealIp(request);
        objectMap.put(client_ip, ipAddress);
        // 获取客户端主机名
        String clientHostName = HttpUtils.getClientHostName(request);
        objectMap.put(client_host, clientHostName);
        // 获取请求开始时间
        String handleStartTime = HANDLE_START_TIME;
        long startTimeMillis = ((Date) request.getAttribute(handleStartTime)).getTime();
        // 接口响应时间（单位：毫秒）
        long responseMillis = System.currentTimeMillis() - startTimeMillis;
        objectMap.put(response_millis, responseMillis);
        interfaceLogDao.saveCallLog(objectMap);
    }

}
