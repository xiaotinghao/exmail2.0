package com.xs.module.exmail.api;

import com.alibaba.fastjson.JSONObject;
import com.xs.module.exmail.token.service.TokenService;
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
 * @author xiaotinghao
 */
@RestController
public class TokenApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenService tokenService;

    /**
     * 通过CorpID和CorpSecret换取ACCESS_TOKEN
     * 每企业调用单个cgi/api不可超过500次/分，15000次/小时
     * 企业每ip调用接口不可超过10000次/分，300000次/小时
     *
     * @param corpId     企业id
     * @param corpSecret 应用的凭证密钥
     * @return ACCESS_TOKEN
     * access_token 获取到的凭证。长度为64至512个字节
     * expires_in 凭证的有效时间（秒）
     */
    @GetMapping(value = "/cgi-bin/gettoken", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getToken(@RequestParam String corpId, @RequestParam String corpSecret) {
        logger.info("接收参数：corpid:{},corpsecret:{}", corpId, corpSecret);
        JSONObject result = tokenService.getToken(corpId, corpSecret);
        logger.info("接口返回结果：{}", result.toString());
        return result.toString();
    }

}
