package com.xs.module.exmail.cgi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml", "/springMvc-servlet.xml"})
public class UserCgiTest {

    @Autowired
    UserCgi userCgi;

    @Test
    public void getLoginUrl() throws Exception {
        String res = userCgi.getLoginUrl("E26ABD9555AED20B4B8B0A4B0F71692CD946ADE493E4DF0EB0B53997D2070FE2", "xth@qq.com");
        System.out.println(res);
    }

}