package com.xs.module.org.model.dto;

import java.util.List;

/**
 * 部门DTO
 *
 * @author xiaotinghao
 */
public class DeptDTO {

    /**
     * 部门ID
     */
    Long deptId;
    /**
     * 部门名称
     */
    String deptName;
    /**
     * 下级部门ID（会有多个）
     */
    List<Long> subDeptIds;
    /**
     * 上级部门ID（仅有一个）
     */
    Long supDeptId;
    /**
     * 部门直接领导岗位
     */
    PostDTO leaderPost;
    /**
     * 部门直接领导
     */
    UserDTO leaderUser;
    /**
     * 部门配置岗位
     */
    List<PostDTO> deptPostList;
    /**
     * 部门配置人员
     */
    List<UserDTO> deptUserList;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<Long> getSubDeptIds() {
        return subDeptIds;
    }

    public void setSubDeptIds(List<Long> subDeptIds) {
        this.subDeptIds = subDeptIds;
    }

    public Long getSupDeptId() {
        return supDeptId;
    }

    public void setSupDeptId(Long supDeptId) {
        this.supDeptId = supDeptId;
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

    public List<PostDTO> getDeptPostList() {
        return deptPostList;
    }

    public void setDeptPostList(List<PostDTO> deptPostList) {
        this.deptPostList = deptPostList;
    }

    public List<UserDTO> getDeptUserList() {
        return deptUserList;
    }

    public void setDeptUserList(List<UserDTO> deptUserList) {
        this.deptUserList = deptUserList;
    }
}
