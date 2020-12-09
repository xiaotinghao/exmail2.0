package com.xs.module.org.service;

import com.xs.module.org.model.dto.DeptDTO;
import com.xs.module.org.model.form.DeptForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class OrgOuterServiceTest {

    @Autowired
    OrgOuterService orgOuterService;

    @Test
    public void getOrgDept() throws Exception {
        DeptForm form = new DeptForm();
        DeptDTO orgDept = orgOuterService.getOrgDept(form);
        Assert.assertNotNull(orgDept);
    }

    @Test
    public void listOrgDept() throws Exception {
        DeptForm form = new DeptForm();
        List<DeptDTO> orgDeptList = orgOuterService.listOrgDept(form);
        Assert.assertNotNull(orgDeptList);
    }

    @Test
    public void getOrgPost() throws Exception {
    }

    @Test
    public void listOrgPost() throws Exception {
    }

    @Test
    public void getOrgUser() throws Exception {
    }

    @Test
    public void listOrgUser() throws Exception {
    }

}