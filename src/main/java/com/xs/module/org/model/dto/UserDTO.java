package com.xs.module.org.model.dto;

public class UserDTO {

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
     * 员工岗位
     */
    PostDTO userPost;
    /**
     * 员工所在部门
     */
    DeptDTO userDept;
    /**
     * 直接领导岗位
     */
    PostDTO leaderPost;
    /**
     * 直接领导用户
     */
    UserDTO leaderUser;

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

    public PostDTO getUserPost() {
        return userPost;
    }

    public void setUserPost(PostDTO userPost) {
        this.userPost = userPost;
    }

    public DeptDTO getUserDept() {
        return userDept;
    }

    public void setUserDept(DeptDTO userDept) {
        this.userDept = userDept;
    }

    public PostDTO getLeaderPost() {
        return leaderPost;
    }

    public void setLeaderPost(PostDTO leaderPost) {
        this.leaderPost = leaderPost;
    }

    public UserDTO getLeaderUser() {
        return leaderUser;
    }

    public void setLeaderUser(UserDTO leaderUser) {
        this.leaderUser = leaderUser;
    }

}
