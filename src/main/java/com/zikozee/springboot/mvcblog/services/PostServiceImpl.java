package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.Post;
import com.zikozee.springboot.mvcblog.repositories.PostRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
@Primary
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private NotificationService notifyService;
    private Logger logger = Logger.getLogger(getClass().getName());

    public PostServiceImpl(PostRepository postRepo, NotificationService notificationService) {
        this.postRepository = postRepo;
        this.notifyService = notificationService;
    }


    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public List<Post> findLatest5() {
        return this.postRepository.findLatest5Posts(PageRequest.of(0, 5));

        //for loop commented out
//        List<Post> last5 = new ArrayList<>();
//        posts.sort(Comparator.comparing(Post::getDate));
//        //posts.sort(Comparator.comparing(Post::getDate).reversed());
//        for(int i=0; i<(posts.size()-(posts.size()-5)); i++){
//            last5.add(posts.get(i));
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

//    @Override
//    public Post createPost(Post post) {
////        post.setId(this.posts.stream().mapToLong(
////                p->p.getId()).max().getAsLong()+1);
////        this.posts.add(post);
////        return post;
//
////        long id = this.posts.size();
////        post.setId(id+1);
////        this.posts.add(post);
////        return post;
//        return this.postRepository.save(post);
//    }

    @Override
    public Post save(Post post) {
//        for(int i=0; i<this.posts.size(); i++){
//            if(Objects.equals(this.posts.get(i).getId(), post.getId())){
//                this.posts.set(i, post);
//                return post;
//            }
//        }
//        return null;
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
