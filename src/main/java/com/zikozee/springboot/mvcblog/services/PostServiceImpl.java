package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.Post;
import com.zikozee.springboot.mvcblog.repositories.PostRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Primary
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private NotificationService notifyService;
    private UserService userService;
    private Logger logger = Logger.getLogger(getClass().getName());

    public PostServiceImpl(PostRepository postRepo, NotificationService notificationService, UserService userService) {
        this.postRepository = postRepo;
        this.notifyService = notificationService;
        this.userService = userService;
    }


    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public List<Post> findLatest5() {
        return this.postRepository.findLatest5Posts(PageRequest.of(0, 5));

//        List<Post> last5 = new ArrayList<>();
//        List<Post> allPost = findAll();
//        //allPost.sort(Comparator.comparing(Post::getDate));
//        allPost.sort(Comparator.comparing(Post::getDate).reversed());
//        for(int i=0; i<(allPost.size()-(allPost.size()-5)); i++){
//            last5.add(allPost.get(i));
//        }
//        return last5;
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
    public Post create(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public Post edit(Post post) {
        return this.postRepository.save(post);
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
        if(id >= this.postRepository.findAll().size() || id <=0){
            notifyService.addErrorMessage("Post not found: " + id);
            return;
        }
        this.postRepository.deleteById(id);
    }

    @Override
    public List<Post> findByAuthor(String username) {
        List<Post> userPosts = new ArrayList<>();

        for(Post post: findAll()){
            //logger.info(post.getAuthor().getUsername());
            if(post.getAuthor().getUsername().equals(username)){
                userPosts.add(post);
            }
        }
        return userPosts;
    }
}
