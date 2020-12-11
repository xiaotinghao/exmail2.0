package com.xs.module.exmail.department.model.bo;

import com.xs.module.exmail.department.model.dto.OrgDTO;
import com.xs.module.exmail.department.model.po.OrgPO;
import com.xs.module.exmail.department.model.vo.OrgVO;

public class OrgBO {

    /**
     * DTO
     */
    OrgDTO orgDto;

    /**
     * PO
     */
    OrgPO orgPo;

    /**
     * VO
     */
    OrgVO orgVo;

    public OrgDTO getOrgDto() {
        return orgDto;
    }

    public void setOrgDto(OrgDTO orgDto) {
        this.orgDto = orgDto;
    }

    public OrgPO getOrgPo() {
        return orgPo;
    }

    public void setOrgPo(OrgPO orgPo) {
        this.orgPo = orgPo;
    }

    public OrgVO getOrgVo() {
        return orgVo;
    }

    public void setOrgVo(OrgVO orgVo) {
        this.orgVo = orgVo;
    }
}
