package com.xs.module.exmail.controller;

import com.xs.module.token.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Token接口
 *
 * @author 18871430207@163.com
 */
@Controller
public class TokenController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenService tokenService;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void test() {
        logger.info("接口返回结果：{}");
    }

}
