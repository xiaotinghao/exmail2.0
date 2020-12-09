package com.xs.module.org.service;

import com.xs.module.org.model.dto.DeptDTO;
import com.xs.module.org.model.dto.PostDTO;
import com.xs.module.org.model.dto.UserDTO;
import com.xs.module.org.model.form.DeptForm;
import com.xs.module.org.model.form.PostForm;
import com.xs.module.org.model.form.UserForm;

import java.util.List;

/**
 * 组织架构外部接口
 *
 * @author xiaotinghao
 */
public interface OrgOuterService {

    /**
     * 查询部门
     */
    DeptDTO getOrgDept(DeptForm form);

    /**
     * 查询部门
     */
    List<DeptDTO> listOrgDept(DeptForm form);

    /**
     * 查询岗位
     */
    PostDTO getOrgPost(PostForm form);

    /**
     * 查询岗位
     */
    List<PostDTO> listOrgPost(PostForm form);

    /**
     * 查询用户
     */
    UserDTO getOrgUser(UserForm form);

    /**
     * 查询用户
     */
    List<UserDTO> listOrgUser(UserForm form);

}
