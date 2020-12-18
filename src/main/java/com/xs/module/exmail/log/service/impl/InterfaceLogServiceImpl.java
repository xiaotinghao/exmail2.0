package com.xs.module.exmail.log.service.impl;

import com.xs.common.dao.ConstantsDao;
import com.xs.common.utils.MapUtils;
import com.xs.common.utils.http.HttpUtils;
import com.xs.module.exmail.log.dao.InterfaceLogDao;
import com.xs.module.exmail.log.service.InterfaceLogService;
import com.xs.module.exmail.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.xs.common.constants.SymbolConstants.HYPHEN;
import static com.xs.module.constants.ExmailConstants.*;

/**
 * 接口日志服务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class InterfaceLogServiceImpl implements InterfaceLogService {

    @Autowired
    InterfaceLogDao interfaceLogDao;
    @Autowired
    ConstantsDao constantsDao;
    @Autowired
    TokenService tokenService;

    @Override
    public void saveLog(String className, String methodName, Object[] args, String[] parameterNames) {
        Map<String, Object> objectMap = MapUtils.init();
        objectMap.put(class_name, className);
        objectMap.put(method_name, methodName);

        objectMap.put(client_ip, HttpUtils.getClientRealIp());
        objectMap.put(client_host, HttpUtils.getClientHostName());

        StringBuilder typeStr = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            String parameterName = parameterNames[i];
            String str = args[i].getClass().toString();
            typeStr.append(",").append(str.substring(str.lastIndexOf(".") + 1));
            if (CORP_ID.equals(parameterName) && String.class.equals(args[i].getClass())) {
                objectMap.put(corp_id, args[i]);
            }
            if (ACCESS_TOKEN.equals(parameterName) && String.class.equals(args[i].getClass())) {
                String accessToken = (String) args[i];
                String[] splits = tokenService.parseToken(accessToken);
                objectMap.put(corp_id, splits.length > 0 ? splits[0] : HYPHEN);
            }
        }
        objectMap.put(arg_types, typeStr.length() > 0 ? typeStr.toString().substring(1) : HYPHEN);
        interfaceLogDao.saveCallLog(objectMap);
    }

}
