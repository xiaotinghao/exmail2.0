package com.xs.module.corp.service.impl;

import com.xs.common.utils.MapUtils;
import daisy.commons.lang3.StringUtils;
import com.xs.common.utils.http.HttpUtils;
import com.xs.framework.interceptor.model.HandleParam;
import com.xs.module.corp.dao.CorpDao;
import com.xs.module.corp.service.CorpService;
import com.xs.module.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.xs.module.constants.ConstantsBase.REQUEST_CORP_ID;
import static com.xs.module.constants.ConstantsToken.REQUEST_ACCESS_TOKEN;
import static com.xs.module.constants.CorpIpRelation.*;

/**
 * 企业相关业务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class CorpServiceImpl implements CorpService {

    @Autowired
    private CorpDao corpDao;

    @Autowired
    TokenService tokenService;

    @Override
    public void saveCorpIpRelation(HandleParam handleParam) {
        HttpServletRequest request = handleParam.getRequest();
        Map<String, Object> objectMap = MapUtils.init();
        Map<String, Object> requestParam = HttpUtils.getRequestParam(request);
        // 获取客户端的真实IP地址
        String clientIp = HttpUtils.getClientRealIp(request);
        if (requestParam.containsKey(REQUEST_CORP_ID)) {
            objectMap.put(corp_id, requestParam.get(REQUEST_CORP_ID));
            objectMap.put(client_ip, clientIp);
        } else if (requestParam.containsKey(REQUEST_ACCESS_TOKEN)) {
            String accessToken = (String) requestParam.get(REQUEST_ACCESS_TOKEN);
            String corpId = tokenService.getCorpId(accessToken);
            if (StringUtils.isNotBlank(corpId)) {
                objectMap.put(corp_id, corpId);
                objectMap.put(client_ip, clientIp);
            }
        }
        if (!objectMap.isEmpty()) {
            // 查询匹配关系是否已存在
            List<Map<String, Object>> queryList = corpDao.listCorpIpRelation(objectMap);
            if (queryList.isEmpty()) {
                corpDao.saveCorpIpRelation(objectMap);
            }
        }
    }

}
