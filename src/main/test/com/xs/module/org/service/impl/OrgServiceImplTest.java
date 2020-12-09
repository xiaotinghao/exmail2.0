package com.xs.module.org.service.impl;

import com.xs.module.org.model.form.OrgForm;
import com.xs.module.org.model.po.UserPO;
import com.xs.module.org.model.vo.OrgVO;
import com.xs.module.org.service.OrgService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class OrgServiceImplTest {

    @Autowired
    OrgService orgService;

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
        OrgVO org = orgService.getOrg(orgForm);
        Assert.assertNotNull(org);
    }

    @Test
    public void listOrg() throws Exception {
    }

}