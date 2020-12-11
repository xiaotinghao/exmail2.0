package com.xs.module.exmail.api;

import com.alibaba.fastjson.JSONObject;
import com.xs.module.exmail.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author xiaotinghao
 */
@RestController
public class UserApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    /**
     * 获取登录企业邮的url
     *
     * @param accessToken 调用接口凭证
     * @param userId      成员UserID
     * @return 登录企业邮的url
     * login_url 登录跳转的url，一次性有效，不可多次使用。
     * expires_in url有效时长，单位为秒
     */
    @GetMapping(value = "/cgi-bin/service/get_login_url", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getLoginUrl(@RequestParam String accessToken, @RequestParam String userId) {
        logger.info("接收参数：access_token:{},userid:{}", accessToken, userId);
        JSONObject result = userService.getLoginUrl(accessToken, userId);
        logger.info("接口返回结果：{}", result.toString());
        return result.toString();
    }

}
