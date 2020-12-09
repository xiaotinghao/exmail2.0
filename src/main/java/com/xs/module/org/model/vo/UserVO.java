package com.xs.module.org.model.vo;

import com.xs.common.utils.StringUtils;

public class UserVO {

    /**
     * 用户ID
     */
    Long userId;
    /**
     * 用户姓名
     */
    String userName;
    /**
     * 1-男，0-女
     */
    Integer gender;
    /**
     * 1-男，0-女
     */
    String genderName;
    /**
     * 员工岗位ID
     */
    Long userPostId;
    /**
     * 员工岗位名称
     */
    String userPostName;
    /**
     * 员工所在部门ID
     */
    Long userDeptId;
    /**
     * 员工所在部门名称
     */
    String userDeptName;
    /**
     * 直接领导岗位ID
     */
    Long leaderPostId;
    /**
     * 直接领导岗位名称
     */
    String leaderPostName;
    /**
     * 直接领导用户ID
     */
    Long leaderUserId;
    /**
     * 直接领导用户名称
     */
    String leaderUserName;

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        if (StringUtils.isNotEmpty(genderName)) {
            this.genderName = genderName;
        } else {
            if ("1".equals(gender)) {
                this.genderName = "帅哥";
            } else if ("0".equals(gender)) {
                this.genderName = "美女";
            } else {
                this.genderName = "";
            }
        }
    }

    public Long getUserPostId() {
        return userPostId;
    }

    public void setUserPostId(Long userPostId) {
        this.userPostId = userPostId;
    }

    public String getUserPostName() {
        return userPostName;
    }

    public void setUserPostName(String userPostName) {
        this.userPostName = userPostName;
    }

    public Long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Long userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public Long getLeaderPostId() {
        return leaderPostId;
    }

    public void setLeaderPostId(Long leaderPostId) {
        this.leaderPostId = leaderPostId;
    }

    public String getLeaderPostName() {
        return leaderPostName;
    }

    public void setLeaderPostName(String leaderPostName) {
        this.leaderPostName = leaderPostName;
    }

    public Long getLeaderUserId() {
        return leaderUserId;
    }

    public void setLeaderUserId(Long leaderUserId) {
        this.leaderUserId = leaderUserId;
    }

    public String getLeaderUserName() {
        return leaderUserName;
    }

    public void setLeaderUserName(String leaderUserName) {
        this.leaderUserName = leaderUserName;
    }
}
