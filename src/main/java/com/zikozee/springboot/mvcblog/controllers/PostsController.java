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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@Slf4j
public class PostsController {
    private PostService postService;
    private NotificationService notifyService;
    private UserService userService;

    @Autowired
    public PostsController(PostService thePostService, NotificationService theNotifyService, UserService userService) {
        this.postService = thePostService;
        this.notifyService = theNotifyService;
        this.userService = userService;
    }

    @GetMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model){
        Post post = postService.findById(id);

        if(post == null){
            notifyService.addErrorMessage("Cannot find post with id #" + id);
            return "redirect:/";
        }
        model.addAttribute("post",post);
        return "posts/view";
    }

    // add mapping for "/posts/index'
    @GetMapping("/posts")
    public String listUserPosts(Model theModel){
        //get posts from database
        Set<Post> posts = postService.findAll();

        // add to the spring model
        theModel.addAttribute("posts", posts);

        return "posts/index";
    }

    @GetMapping("/posts/create")
    public String createPost(Model theModel){

        Post post = new Post();

        theModel.addAttribute("post", post);
        return "posts/create";
    }

    @GetMapping("/posts/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("postId") Long theId, Model theModel){
        Post post = postService.findById(theId);
        theModel.addAttribute("post", post);
        return "posts/create";
    }

    @PostMapping("/posts/save")
    public String savePost(@ModelAttribute("post") Post post){
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        // takes care of author_id
        post.setAuthor(user);
        postService.create_edit(post);
        notifyService.addInfoMessage("Post created successfully");
        return "redirect:/posts";
    }

    @GetMapping("/posts/delete")
    public String showFormForDelete(@RequestParam("postId") Long theId, RedirectAttributes redirectAttributes) {

        postService.deleteById(theId);

        redirectAttributes.addFlashAttribute("message", "deleted successfully");

        return "redirect:/users/post";
    }
}
