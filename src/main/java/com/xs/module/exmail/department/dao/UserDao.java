package com.xs.module.exmail.department.dao;

import com.xs.module.exmail.department.model.dto.UserDTO;
import com.xs.module.exmail.department.model.form.UserForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    int saveUser(UserForm form);

    int deleteUser(UserForm form);

    int updateUser(UserForm form);

    UserDTO getUser(UserForm form);

    List<UserDTO> listUser(UserForm form);

}
