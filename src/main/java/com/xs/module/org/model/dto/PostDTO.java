package com.xs.module.org.model.dto;

import java.util.List;

/**
 * 岗位DTO
 *
 * @author xiaotinghao
 */
public class PostDTO {

    /**
     * 岗位ID
     */
    Long postId;
    /**
     * 岗位名称
     */
    String postName;
    /**
     * 岗位所属部门
     */
    DeptDTO postDept;
    /**
     * 岗位对应员工ID
     */
    UserDTO postUser;
    /**
     * 直接管辖的部门
     */
    List<DeptDTO> mgtDeptList;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public DeptDTO getPostDept() {
        return postDept;
    }

    public void setPostDept(DeptDTO postDept) {
        this.postDept = postDept;
    }

    public UserDTO getPostUser() {
        return postUser;
    }

    public void setPostUser(UserDTO postUser) {
        this.postUser = postUser;
    }

    public List<DeptDTO> getMgtDeptList() {
        return mgtDeptList;
    }

    public void setMgtDeptList(List<DeptDTO> mgtDeptList) {
        this.mgtDeptList = mgtDeptList;
    }
}
