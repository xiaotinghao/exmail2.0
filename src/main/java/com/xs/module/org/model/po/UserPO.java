package com.xs.module.org.model.po;

import com.xs.common.entity.QueryEntity;

/**
 * 用户实体
 *
 * @author xiaotinghao
 */
public class UserPO extends QueryEntity {
    /**
     * 用户ID（主键）
     */
    Long userId;
    /**
     * 姓名
     */
    String userName;
    /**
     * 密码
     */
    String password;
    /**
     * 1-男，0-女
     */
    String gender;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
