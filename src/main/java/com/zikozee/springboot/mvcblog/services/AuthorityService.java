package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.model.Authority;

import java.util.Set;

public interface AuthorityService {

    Set<Authority> findAll();

    Set<Authority> findByUsername(String username);
}
