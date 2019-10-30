package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.model.Authority;
import com.zikozee.springboot.mvcblog.model.BlogUser;
import com.zikozee.springboot.mvcblog.model.User;
import com.zikozee.springboot.mvcblog.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private NotificationService notifyService;
    private final PasswordEncoder passwordEncoder;
    private AuthorityService authorityService;
    private PostService postService;

    public UserServiceImpl(UserRepository userRepository, NotificationService notifyService,
                           PasswordEncoder passwordEncoder, AuthorityService authorityService, PostService postService) {
        this.userRepository = userRepository;
        this.notifyService = notifyService;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
        this.postService = postService;
    }

    @Override
    public Set<User> findAll() {
        return new HashSet<>(userRepository.findAll());
    }

    @Override
    public User findById(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        User user = null;
        if(result.isPresent()){
            user = result.get();
        }else{
            // we didn't find the employee
            //throw new RuntimeException("Did not find employee id - " + id);
            notifyService.addErrorMessage("Did not find user id - " + id);
        }
        return user;
    }

    @Override
    public User create(BlogUser blogUser) {
        // assign user details to the user object
        User user = new User();
        user.setUsername(blogUser.getUsername());
        user.setPasswordHash(passwordEncoder.encode(blogUser.getPassword()));
        user.setFullName(blogUser.getFullName());
        user.setEnabled(true);

//        // give user default role of "USER"
        Authority authority = new Authority(user.getUsername(), "ROLE_USER");
        Set<Authority> authorityList = new HashSet<>();
        authorityList.add(authority);
        user.setAuthorities(authorityList);
        //user.getAuthorities().add(authority);   // all the authority above for this line of code
        userRepository.save(user);
        return user;
    }

    @Override
    public void edit(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {

        //this.postService.deleteByAuthorId(id);
        this.userRepository.deleteById(id);
    }

    @Override
    public User findByUserName(String userName) {
        for(User user :  userRepository.findAll()){
            if(user.getUsername().equals(userName)){
                return user;
            }
        }
        return null;
    }
}
