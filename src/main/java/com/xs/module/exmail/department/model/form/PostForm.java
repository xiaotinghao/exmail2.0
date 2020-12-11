package com.xs.module.exmail.department.model.form;

import com.xs.module.exmail.department.model.po.PostPO;

/**
 * 岗位查询对象
 *
 * @author xiaotinghao
 */
public class PostForm extends PostPO {

    /**
     * 查询条件：岗位ID（多个用英文逗号分隔）
     */
    String postIds;

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds;
    }

}
