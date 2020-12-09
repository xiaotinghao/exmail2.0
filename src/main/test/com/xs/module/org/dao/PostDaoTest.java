package com.xs.module.org.dao;

import com.xs.module.org.model.dto.PostDTO;
import com.xs.module.org.model.form.PostForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class PostDaoTest {

    @Autowired
    PostDao postDao;

    @Test
    public void savePost() throws Exception {
    }

    @Test
    public void deletePost() throws Exception {
    }

    @Test
    public void updatePost() throws Exception {
    }

    @Test
    public void getPost() throws Exception {
        PostForm postForm = new PostForm();
        postForm.setPostId(1L);
        PostDTO postDto = postDao.getPost(postForm);
        Assert.assertNotNull(postDto);
    }

    @Test
    public void listPost() throws Exception {
        PostForm postForm = new PostForm();
        postForm.setPostName("岗位");
        List<PostDTO> postDtoList = postDao.listPost(postForm);
        Assert.assertNotNull(postDtoList);
    }

}