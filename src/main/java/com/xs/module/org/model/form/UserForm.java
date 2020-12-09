package com.xs.module.org.model.form;

import com.xs.module.org.model.po.UserPO;

/**
 * 用户查询对象
 *
 * @author xiaotinghao
 */
public class UserForm extends UserPO {

    /**
     * 查询条件：部门ID
     */
    Long deptId;
    /**
     * 查询条件：岗位ID（多个用英文逗号分隔）
     */
    String postIds;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds;
    }

}
