package com.xs.module.org.model.vo;


import com.xs.module.org.model.dto.DeptDTO;

import java.util.List;

public class PostVO {

    /**
     * 岗位ID
     */
    Long postId;
    /**
     * 岗位名称
     */
    String postName;
    /**
     * 岗位所属部门ID
     */
    Long postDeptId;
    /**
     * 岗位所属部门名称
     */
    String postDeptName;
    /**
     * 岗位对应员工ID
     */
    Long postUserId;
    /**
     * 岗位对应员工姓名
     */
    String postUserName;
    /**
     * 直接管理部门
     */
    List<DeptDTO> mgtDeptList;


}
