package com.xs.module.exmail.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserApiTest {

    @Autowired
    UserApi userApi;

    @Test
    public void getLoginUrl() throws Exception {
        String result = userApi.getLoginUrl("1%21", "xth@qq.com");
        System.out.println(result);
        assertNotNull(result);
    }

}