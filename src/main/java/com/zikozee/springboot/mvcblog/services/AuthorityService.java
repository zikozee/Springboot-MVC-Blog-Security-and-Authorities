package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.Authority;

import java.util.HashSet;
import java.util.List;

public interface AuthorityService {

    List<Authority> findAll();
    void save(Authority authority);
    void delete(Long id);
    HashSet<Authority> findByUsername(String username);
}
