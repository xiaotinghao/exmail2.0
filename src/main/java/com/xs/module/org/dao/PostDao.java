package com.xs.module.org.dao;

import com.xs.module.org.model.dto.PostDTO;
import com.xs.module.org.model.form.PostForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao {

    int savePost(PostForm form);

    int deletePost(PostForm form);

    int updatePost(PostForm form);

    PostDTO getPost(PostForm form);

    List<PostDTO> listPost(PostForm form);

}
