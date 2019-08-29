package com.zikozee.springboot.mvcblog.controllers;

import com.zikozee.springboot.mvcblog.models.Post;
import com.zikozee.springboot.mvcblog.models.User;
import com.zikozee.springboot.mvcblog.services.NotificationService;
import com.zikozee.springboot.mvcblog.services.PostService;
import com.zikozee.springboot.mvcblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserController {
    private UserService userService;
    private PostService postService;
    private NotificationService notifyService;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserController(UserService theUserService, PostService postService, NotificationService theNotifyService) {
        this.userService = theUserService;
        this.postService = postService;
        this.notifyService = theNotifyService;

    }

    @GetMapping("/users")
    public String ListUsers(Model theModel){
        List<User> users =  userService.findAll();
        theModel.addAttribute("users", users);

        return "users/index";
    }

    @GetMapping("/users/post")
    public String ListUserPost(Model theModel){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //logger.info(username);
        List<Post> userPosts =  postService.findByAuthor(username);
        if(userPosts == null ||  userPosts.isEmpty()){
            notifyService.addErrorMessage(username +" has not posted");
            return "redirect:/";
        }
        theModel.addAttribute("userPosts", userPosts);

        return "posts/userPostIndex";
    }

    @GetMapping("/users/delete")
    public String showFormForDelete(@RequestParam("userId") Long theId){
        userService.deleteById(theId);

        // redirect to /employee/list
        return "redirect:/users";
    }
}
