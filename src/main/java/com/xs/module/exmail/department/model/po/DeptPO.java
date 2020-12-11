package com.xs.module.exmail.department.model.po;

import com.xs.common.entity.QueryEntity;

/**
 * 部门实体
 *
 * @author xiaotinghao
 */
public class DeptPO extends QueryEntity {
    /**
     * 部门ID（主键）
     */
    Long deptId;
    /**
     * 部门名称
     */
    String deptName;
    /**
     * 部门编码
     */
    String deptCode;
    /**
     * 部门领导岗位ID
     */
    Long leaderPostId;

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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Long getLeaderPostId() {
        return leaderPostId;
    }

    public void setLeaderPostId(Long leaderPostId) {
        this.leaderPostId = leaderPostId;
    }
}
