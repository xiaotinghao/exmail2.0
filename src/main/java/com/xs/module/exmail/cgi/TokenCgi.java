package com.xs.module.exmail.cgi;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.annotation.InterfaceLog;
import com.xs.module.token.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token接口
 *
 * @author 18871430207@163.com
 */
@RestController
public class TokenCgi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenService tokenService;

    /**
     * 通过CorpID和CorpSecret换取ACCESS_TOKEN
     *
     * @param corpId     企业id
     * @param corpSecret 应用的凭证密钥
     * @return ACCESS_TOKEN
     * access_token 获取到的凭证。长度为64至512个字节
     * expires_in 凭证的有效时间（毫秒）
     */
    @GetMapping(value = "/cgi-bin/gettoken", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @InterfaceLog
    public String getToken(@RequestParam String corpId, @RequestParam String corpSecret) {
        logger.info("接收参数：corpid:{},corpsecret:{}", corpId, corpSecret);
        JSONObject result = tokenService.getToken(corpId, corpSecret);
        logger.info("接口返回结果：{}", result.toString());
        return result.toString();
    }

}
