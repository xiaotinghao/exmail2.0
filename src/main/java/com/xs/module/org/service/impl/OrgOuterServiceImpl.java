package com.xs.module.org.service.impl;

import com.xs.module.org.dao.DeptDao;
import com.xs.module.org.dao.PostDao;
import com.xs.module.org.dao.UserDao;
import com.xs.module.org.model.dto.DeptDTO;
import com.xs.module.org.model.dto.PostDTO;
import com.xs.module.org.model.dto.UserDTO;
import com.xs.module.org.model.form.DeptForm;
import com.xs.module.org.model.form.PostForm;
import com.xs.module.org.model.form.UserForm;
import com.xs.module.org.service.OrgOuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgOuterServiceImpl implements OrgOuterService {

    @Autowired
    DeptDao deptDao;

    @Autowired
    PostDao postDao;

    @Autowired
    UserDao userDao;

    @Override
    public DeptDTO getOrgDept(DeptForm form) {
        return deptDao.getDept(form);
    }

    @Override
    public List<DeptDTO> listOrgDept(DeptForm form) {
        return deptDao.listDept(form);
    }

    @Override
    public PostDTO getOrgPost(PostForm form) {
        return postDao.getPost(form);
    }

    @Override
    public List<PostDTO> listOrgPost(PostForm form) {
        return postDao.listPost(form);
    }

    @Override
    public UserDTO getOrgUser(UserForm form) {
        return userDao.getUser(form);
    }

    @Override
    public List<UserDTO> listOrgUser(UserForm form) {
        return userDao.listUser(form);
    }

}
