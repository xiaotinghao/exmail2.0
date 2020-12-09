package com.xs.module.org.dao;

import com.xs.module.org.model.dto.UserDTO;
import com.xs.module.org.model.form.UserForm;
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
