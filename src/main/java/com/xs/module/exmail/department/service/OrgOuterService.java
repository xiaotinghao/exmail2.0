package com.xs.module.exmail.department.service;

import com.xs.module.exmail.department.model.dto.UserDTO;
import com.xs.module.exmail.department.model.dto.DeptDTO;
import com.xs.module.exmail.department.model.dto.PostDTO;
import com.xs.module.exmail.department.model.form.DeptForm;
import com.xs.module.exmail.department.model.form.PostForm;
import com.xs.module.exmail.department.model.form.UserForm;

import java.util.List;

/**
 * 组织架构外部接口
 *
 * @author 18871430207@163.com
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
