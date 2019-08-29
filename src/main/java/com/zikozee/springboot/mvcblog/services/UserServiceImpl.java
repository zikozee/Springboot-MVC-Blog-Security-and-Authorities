package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.Authority;
import com.zikozee.springboot.mvcblog.models.BlogUser;
import com.zikozee.springboot.mvcblog.models.User;
import com.zikozee.springboot.mvcblog.repositories.AuthorityRepository;
import com.zikozee.springboot.mvcblog.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private NotificationService notifyService;
    private final PasswordEncoder passwordEncoder;
    private AuthorityRepository authorityRepository;

    private Logger logger = Logger.getLogger(getClass().getName());

    public UserServiceImpl(UserRepository userRepository, NotificationService notifyService,
                           PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.notifyService = notifyService;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
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
            notifyService.addErrorMessage("Did not find Post id - " + id);
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
        userRepository.save(user);
//        // give user default role of "USER"
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUsername(blogUser.getUsername());
        authorityRepository.save(authority);

        return user;
    }

    @Override
    public User edit(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        if(id > findAll().size() || id <=0){
            logger.info(id.toString() + " " + findAll().size() + " last " + findAll().get(findAll().size()-2).getId());
            notifyService.addErrorMessage("User id not found: " + id);
            return;
        }
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


    @Override
    public void saveExistingUser(BlogUser blogUser) {

    }
}
