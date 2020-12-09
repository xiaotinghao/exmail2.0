package com.xs.module.org.model.vo;

import java.util.List;

public class DeptVO {
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
     * 部门直接领导岗位ID
     */
    String leaderPostId;
    /**
     * 部门配置岗位
     */
    List<Long> deptPostIds;



}
