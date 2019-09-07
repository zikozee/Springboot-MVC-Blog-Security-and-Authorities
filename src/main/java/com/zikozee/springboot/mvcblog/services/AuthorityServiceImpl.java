package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.Authority;
import com.zikozee.springboot.mvcblog.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class AuthorityServiceImpl implements AuthorityService {
    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public List<Authority> findByUsername(String username) {
        List<Authority> userAuthorities = new LinkedList<>();

        for(Authority authority : findAll()){
            if(authority.getUsername().equals(username)){
                userAuthorities.add(authority);
            }
        }
        return userAuthorities;
    }

}
