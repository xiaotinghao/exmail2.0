package com.xs.module.org.model.dto;

import java.util.List;

public class OrgDTO {

    /**
     * 部门
     */
    List<DeptDTO> deptList;
    /**
     * 岗位
     */
    List<PostDTO> postList;
    /**
     * 用户
     */
    List<UserDTO> userList;

    public List<DeptDTO> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptDTO> deptList) {
        this.deptList = deptList;
    }

    public List<PostDTO> getPostList() {
        return postList;
    }

    public void setPostList(List<PostDTO> postList) {
        this.postList = postList;
    }

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }
}
