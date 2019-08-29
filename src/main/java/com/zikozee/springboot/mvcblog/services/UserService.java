package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.BlogUser;
import com.zikozee.springboot.mvcblog.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User create(BlogUser blogUser);
    User edit(User user);
    void deleteById(Long id);

    User findByUserName(String userName);
    void saveExistingUser(BlogUser blogUser);
}
