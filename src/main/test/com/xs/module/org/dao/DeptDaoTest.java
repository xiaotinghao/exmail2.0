package com.xs.module.org.dao;

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
public class DeptDaoTest {

    @Autowired
    DeptDao deptDao;

    @Test
    public void saveDept() throws Exception {
    }

    @Test
    public void deleteDept() throws Exception {
    }

    @Test
    public void updateDept() throws Exception {
    }

    @Test
    public void getDept() throws Exception {
        DeptForm deptForm = new DeptForm();
        deptForm.setDeptId(1L);
        DeptDTO deptDto = deptDao.getDept(deptForm);
        Assert.assertNotNull(deptDto);
    }

    @Test
    public void listDept() throws Exception {
        DeptForm deptForm = new DeptForm();
        deptForm.setDeptName("部门");
        List<DeptDTO> deptDtoList = deptDao.listDept(deptForm);
        Assert.assertNotNull(deptDtoList);
    }

}