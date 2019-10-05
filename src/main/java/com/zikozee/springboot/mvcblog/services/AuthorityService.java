package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.model.Authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> findAll();
    List<Authority> findByUsername(String username);
}
