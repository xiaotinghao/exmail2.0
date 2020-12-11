package com.xs.module.register.service.impl;

import com.xs.module.register.dao.CorpRegisterDao;
import com.xs.module.register.service.CorpRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.xs.common.constants.NumberConstants.ZERO;

/**
 * 企业注册接口服务接口实现
 *
 * @author xiaotinghao
 */
@Service
public class CorpRegisterServiceImpl implements CorpRegisterService {

    @Autowired
    CorpRegisterDao corpRegisterDao;

    @Override
    public boolean valid(String corpId, String corpSecret) {
        int result = corpRegisterDao.valid(corpId, corpSecret);
        return result != ZERO;
    }

}
