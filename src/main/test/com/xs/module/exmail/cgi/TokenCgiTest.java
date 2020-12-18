package com.xs.module.exmail.cgi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml", "/springMvc-servlet.xml"})
public class TokenCgiTest {

    @Autowired
    TokenCgi tokenCgi;

    @Test
    public void getToken() throws Exception {
        String token = tokenCgi.getToken("ruian", "app00001");
        System.out.println(token);
    }

}