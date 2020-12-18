package com.xs.module.exmail.department.model.po;

import com.xs.common.entity.QueryEntity;

/**
 * 组织实体
 *
 * @author 18871430207@163.com
 */
public class OrgPO extends QueryEntity {

    /**
     * 部门
     */
    DeptPO deptPo;
    /**
     * 岗位
     */
    PostPO postPo;
    /**
     * 用户
     */
    UserPO userPo;

    public DeptPO getDeptPo() {
        return deptPo;
    }

    public void setDeptPo(DeptPO deptPo) {
        this.deptPo = deptPo;
    }

    public PostPO getPostPo() {
        return postPo;
    }

    public void setPostPo(PostPO postPo) {
        this.postPo = postPo;
    }

    public UserPO getUserPo() {
        return userPo;
    }

    public void setUserPo(UserPO userPo) {
        this.userPo = userPo;
    }
}
