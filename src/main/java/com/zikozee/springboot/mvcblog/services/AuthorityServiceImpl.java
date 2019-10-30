package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.model.Authority;
import com.zikozee.springboot.mvcblog.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Set<Authority> findAll() {
        return new HashSet<>(authorityRepository.findAll());
    }

    @Override
    public Set<Authority> findByUsername(String username) {
//        Set<Authority> userAuthorities = new HashSet<>();
//
//        for(Authority authority : findAll()){
//            if(authority.getUsername().equals(username)){
//                userAuthorities.add(authority);
//            }
//        }
//        return userAuthorities;
        return findAll()
                .parallelStream()
                .filter(authority -> authority.getUsername().equals(username))
                .collect(Collectors.toSet());
    }

}
