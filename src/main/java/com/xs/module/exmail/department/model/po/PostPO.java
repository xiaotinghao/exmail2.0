package com.xs.module.exmail.department.model.po;

import com.xs.common.model.PageQuery;

/**
 * 岗位实体
 *
 * @author 18871430207@163.com
 */
public class PostPO extends PageQuery {
    /**
     * 岗位ID
     */
    Long postId;
    /**
     * 岗位名称
     */
    String postName;

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
}
