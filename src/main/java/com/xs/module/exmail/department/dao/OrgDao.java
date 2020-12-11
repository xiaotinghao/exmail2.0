package com.xs.module.exmail.department.dao;

import com.xs.module.exmail.department.model.dto.OrgDTO;
import com.xs.module.exmail.department.model.form.OrgForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgDao {

    int saveOrg(OrgForm form);

    int deleteOrg(OrgForm form);

    int updateOrg(OrgForm form);

    OrgDTO getOrg(OrgForm form);

    List<OrgDTO> listOrg(OrgForm form);

}
