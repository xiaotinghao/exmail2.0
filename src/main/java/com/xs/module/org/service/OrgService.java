package com.xs.module.org.service;

import com.xs.module.org.model.form.OrgForm;
import com.xs.module.org.model.vo.OrgVO;

import java.util.List;

/**
 * 组织接口
 */
public interface OrgService {

    int saveOrg(OrgForm form);

    int deleteOrg(OrgForm form);

    int updateOrg(OrgForm form);

    OrgVO getOrg(OrgForm form);

    List<OrgVO> listOrg(OrgForm form);

}
