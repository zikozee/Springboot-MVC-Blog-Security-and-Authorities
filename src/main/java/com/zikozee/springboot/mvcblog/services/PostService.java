package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    List<Post> findLatest5();
    Post findById(Long id);
    //Post createPost(Post post);
    Post save(Post post);
    void deleteById(Long id);
    List<Post> findByAuthor(String username);
}
