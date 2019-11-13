package com.zikozee.springboot.mvcblog.controllers;

import com.zikozee.springboot.mvcblog.model.Post;
import com.zikozee.springboot.mvcblog.model.User;
import com.zikozee.springboot.mvcblog.services.NotificationService;
import com.zikozee.springboot.mvcblog.services.PostService;
import com.zikozee.springboot.mvcblog.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@Slf4j
public class UserController {

    private UserService userService;
    private PostService postService;
    private NotificationService notifyService;

    @Autowired
    public UserController(UserService theUserService, PostService postService, NotificationService theNotifyService) {
        this.userService = theUserService;
        this.postService = postService;
        this.notifyService = theNotifyService;
    }

    @GetMapping("/users")
    public String ListUsers(Model theModel){
        Set<User> users = userService.findAll();
        theModel.addAttribute("users", users);

        return "users/index";
    }

    @GetMapping("/users/post")
    public String listUserPost(Model theModel) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Set<Post> userPosts =  postService.findByAuthor(username);
        if(userPosts == null ||  userPosts.isEmpty()){
            notifyService.addErrorMessage(username +" has not posted");
            return "redirect:/";
        }
        String some = (String) theModel.asMap().get("message");
        if (some != null) {
            notifyService.addInfoMessage(some);
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
