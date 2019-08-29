package com.zikozee.springboot.mvcblog.controllers;

import com.zikozee.springboot.mvcblog.models.Post;
import com.zikozee.springboot.mvcblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private PostService postService;

    @Autowired
    public HomeController(PostService thePostService) {
        postService = thePostService;
    }

    @GetMapping("/")
    public String index(Model model){
        List<Post> lastest5Posts = postService.findLatest5();
        model.addAttribute("latest5Posts", lastest5Posts);

        List<Post> lastest3Posts = lastest5Posts.stream().
                limit(3).collect(Collectors.toList());

//        for loop commented out
//        List<Post> lastest3Posts = new ArrayList<>();
//        //lastest5Posts.sort(Comparator.comparing(Post::getDate));
//        lastest5Posts.sort(Comparator.comparing(Post::getDate).reversed());
//        for(int i=0; i<(lastest5Posts.size()-2); i++){
//            lastest3Posts.add(lastest5Posts.get(i));
//        }
        model.addAttribute("latest3Posts", lastest3Posts);
        return "index";
    }
}
