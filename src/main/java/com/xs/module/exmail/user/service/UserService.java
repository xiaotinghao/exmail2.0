package com.xs.module.exmail.user.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户服务接口
 *
 * @author 18871430207@163.com
 */
public interface UserService {

    /**
     * 获取登录企业邮的url
     *
     * @param accessToken 调用接口凭证
     * @param userId      成员UserID
     * @return 登录企业邮的url
     * login_url 登录跳转的url，一次性有效，不可多次使用。
     * expires_in url有效时长，单位为秒
     */
    JSONObject getLoginUrl(String accessToken, String userId);

}
