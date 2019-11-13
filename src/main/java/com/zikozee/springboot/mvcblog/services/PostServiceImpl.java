package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.model.Post;
import com.zikozee.springboot.mvcblog.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Primary
@Service
@Slf4j
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private NotificationService notifyService;
    private Logger logger = Logger.getLogger(getClass().getName());

    //used @Lazy to prevent bean circle_loop--->> occurs only in constructor injection
    //another option we can use setter injection like below since field injection is not recommended
    public PostServiceImpl(PostRepository postRepo, NotificationService notificationService, @Lazy UserService userService) {
        this.postRepository = postRepo;
        this.notifyService = notificationService;
    }

//    public void LoadMe(UserService userService){
//        this.userService = userService;
//    }

    @Override
    public Set<Post> findAll() {
        return new HashSet<>(postRepository.findByOrderByDateAsc());
    }

    @Override
    public List<Post> findLatest5() {
        return this.postRepository.findLatest5Posts(PageRequest.of(0, 5));
    }

    @Override
    public Post findById(Long id) {
        Optional<Post> result = postRepository.findById(id);
        Post post = null;
        if(result.isPresent()){
            post = result.get();
        }else{
            // we didn't find the employee
            //throw new RuntimeException("Did not find employee id - " + id);
            notifyService.addErrorMessage("Did not find Post id - " + id);
        }

        return post;

    }

    @Override
    public void create_edit(Post post) {
        this.postRepository.save(post);
    }


    @Override
    public void deleteById(Long id) {
//        for(int i=0; i<this.posts.size(); i++){
//            if(Objects.equals(this.posts.get(i).getId(), id)){
//                this.posts.remove(i);
//                return;
//            }
//        }
//        throw new RuntimeException("Post not found: " + id);
        if (!this.findAll().contains(findById(id)) || id <= 0) {
            log.info("id>>>>>>>.. " + id + " size: " + findAll().size());
            notifyService.addErrorMessage("Post not found: " + id);
            return;
        }
        this.postRepository.deleteById(id);
    }


    @Override
    public Set<Post> findByAuthor(String username) {
//        Set<Post> userPosts = new HashSet<>();
//
//        for(Post post: findAll()){
//            //logger.info(post.getAuthor().getUsername());
//            if(post.getAuthor().getUsername().equals(username)){
//                userPosts.add(post);
//            }
//        }
//        return userPosts;

        return findAll()
                .stream()
                .filter(post -> post.getAuthor().getUsername().equals(username))
                .collect(Collectors.toSet());
    }

    @Override
    public List<Post> homePosts(Model model) {
        List<Post> lastest5Posts = findLatest5();
        model.addAttribute("latest5Posts", lastest5Posts);

        return lastest5Posts.stream().
                limit(3).collect(Collectors.toList());
    }
}
