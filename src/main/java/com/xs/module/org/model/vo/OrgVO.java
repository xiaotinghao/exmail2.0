package com.xs.module.org.model.vo;

import java.util.List;

public class OrgVO {

    /**
     * 部门
     */
    DeptVO deptVo;
    /**
     * 岗位
     */
    List<PostVO> postVoList;
    /**
     * 用户
     */
    List<UserVO> userVo;

    public DeptVO getDeptVo() {
        return deptVo;
    }

    public void setDeptVo(DeptVO deptVo) {
        this.deptVo = deptVo;
    }

    public List<PostVO> getPostVoList() {
        return postVoList;
    }

    public void setPostVoList(List<PostVO> postVoList) {
        this.postVoList = postVoList;
    }

    public List<UserVO> getUserVo() {
        return userVo;
    }

    public void setUserVo(List<UserVO> userVo) {
        this.userVo = userVo;
    }

}
