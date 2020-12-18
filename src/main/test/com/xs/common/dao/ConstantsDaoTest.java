package com.xs.common.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml", "/springMvc-servlet.xml"})
public class ConstantsDaoTest {

    @Autowired
    ConstantsDao constantsDao;

    @Test
    public void listConstants() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("t_constants_base,t_constants_token");
        list.add("t_constants_token");
        String tables = constantsDao.constantsTables();
        List<Map<String, Object>> maps = constantsDao.listConstants(tables);
        System.out.println(maps);
    }

    @Test
    public void getBaseConstants() throws Exception {
        String baseConstants = constantsDao.getBaseConstants("MODULE_TOKEN");
        System.out.println(baseConstants);
        assertNotNull(baseConstants);
    }

    @Test
    public void getModuleConstants() throws Exception {
        String moduleConstants = constantsDao.getModuleConstants("TOKEN_ENCRYPTION_KEY", "token");
        System.out.println(moduleConstants);
        assertNotNull(moduleConstants);
    }

    @Test
    public void listBaseConstants() throws Exception {
        List<String> list = constantsDao.listBaseConstants("MODULE_TOKEN");
        System.out.println(list);
        assertNotNull(list);
    }

    @Test
    public void listModuleConstants() throws Exception {
        List<String> list = constantsDao.listModuleConstants("TOKEN_ENCRYPTION_KEY", "token");
        System.out.println(list);
        assertNotNull(list);
    }

}