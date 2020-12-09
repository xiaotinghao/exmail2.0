package com.xs.module.org.dao;

import com.xs.module.org.model.dto.OrgDTO;
import com.xs.module.org.model.form.OrgForm;
import com.xs.module.org.model.po.UserPO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class OrgDaoTest {

    @Autowired
    OrgDao orgDao;

    @Test
    public void saveOrg() throws Exception {
    }

    @Test
    public void deleteOrg() throws Exception {
    }

    @Test
    public void updateOrg() throws Exception {
    }

    @Test
    public void getOrg() throws Exception {
        OrgForm orgForm = new OrgForm();
        UserPO usePo = new UserPO();
//        usePo.setUserId(1L);
        usePo.setUserName("员工");
        orgForm.setUserPo(usePo);
        OrgDTO orgDto = orgDao.getOrg(orgForm);
        Assert.assertNotNull(orgDto);
    }

    @Test
    public void listOrg() throws Exception {
        OrgForm orgForm = new OrgForm();
        UserPO usePo = new UserPO();
//        usePo.setUserId(1L);
        usePo.setUserName("员工");
        orgForm.setUserPo(usePo);
        List<OrgDTO> orgDtoList = orgDao.listOrg(orgForm);
        Assert.assertNotNull(orgDtoList);
    }

}