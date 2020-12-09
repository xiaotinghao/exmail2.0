package com.xs.module.org.dao;

import com.xs.module.org.model.dto.UserDTO;
import com.xs.module.org.model.form.UserForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    public void saveUser() throws Exception {
    }

    @Test
    public void deleteUser() throws Exception {
    }

    @Test
    public void updateUser() throws Exception {
    }

    @Test
    public void getUser() throws Exception {
        UserForm userForm = new UserForm();
        userForm.setUserId(1L);
        userForm.setUserName("员工");
        UserDTO user = userDao.getUser(userForm);
        Assert.assertNotNull(user);
    }

    @Test
    public void listUser() throws Exception {
    }

}