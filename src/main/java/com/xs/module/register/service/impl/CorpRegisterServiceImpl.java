package com.xs.module.register.service.impl;

import com.xs.module.register.dao.CorpRegisterDao;
import com.xs.module.register.service.CorpRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业注册接口服务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class CorpRegisterServiceImpl implements CorpRegisterService {

    @Autowired
    CorpRegisterDao corpRegisterDao;

    @Override
    public boolean valid(String corpId, String corpSecret) {
        return corpRegisterDao.valid(corpId, corpSecret) != 0;
    }

}
