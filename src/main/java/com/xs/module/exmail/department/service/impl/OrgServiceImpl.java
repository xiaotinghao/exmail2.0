package com.xs.module.exmail.department.service.impl;

import com.xs.module.exmail.department.model.bo.OrgBO;
import com.xs.module.exmail.department.model.dto.OrgDTO;
import com.xs.module.exmail.department.dao.OrgDao;
import com.xs.module.exmail.department.model.form.OrgForm;
import com.xs.module.exmail.department.model.vo.OrgVO;
import com.xs.module.exmail.department.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    OrgDao orgDao;

    @Override
    public int saveOrg(OrgForm form) {
        return 0;
    }

    @Override
    public int deleteOrg(OrgForm form) {
        return 0;
    }

    @Override
    public int updateOrg(OrgForm form) {
        return 0;
    }

    @Override
    public OrgVO getOrg(OrgForm form) {
        OrgDTO orgDto = orgDao.getOrg(form);

        OrgBO orgBo = new OrgBO();
        orgBo.setOrgDto(orgDto);
        // 调用业务处理
        test(orgBo);

        return orgBo.getOrgVo();
    }

    @Override
    public List<OrgVO> listOrg(OrgForm form) {
        return null;
    }

    private void test(OrgBO orgBo) {
        OrgDTO orgDto = orgBo.getOrgDto();
        OrgVO orgVo = orgBo.getOrgVo();
        if (orgDto != null) {
            // dto 转 vo
//            orgVo.setDeptVo(XsBeanUtils.dtoToVo(orgDto.getDeptDto(), DeptVO.class));
//            orgVo.setPostVo(XsBeanUtils.dtoToVo(orgDto.getPostDto(), PostVO.class));
//            orgVo.setUserVo(XsBeanUtils.dtoToVo(orgDto.getUserDto(), UserVO.class));
        }
    }

}
