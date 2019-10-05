package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.model.Post;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

public interface PostService {
    List<Post> findAll();
    List<Post> findLatest5();
    Post findById(Long id);

    void create_edit(Post post);
    void deleteById(Long id);
    Set<Post> findByAuthor(String username);

    List<Post> homePosts(Model model);
}
