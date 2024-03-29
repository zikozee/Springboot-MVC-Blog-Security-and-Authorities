package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.model.BlogUser;
import com.zikozee.springboot.mvcblog.model.User;

import java.util.Set;

public interface UserService {
    Set<User> findAll();
    User findById(Long id);
    User create(BlogUser blogUser);

    void edit(User user);
    void deleteById(Long id);
    User findByUserName(String userName);
}
