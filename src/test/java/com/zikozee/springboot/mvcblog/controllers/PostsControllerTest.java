package com.zikozee.springboot.mvcblog.controllers;

import com.zikozee.springboot.mvcblog.model.Post;
import com.zikozee.springboot.mvcblog.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class PostsControllerTest {
    @Mock
    PostService postService;

    @InjectMocks
    PostsController controller;

    MockMvc mockMvc;

    Set<Post> posts;

    @BeforeEach
    void setUp() {

        posts = new HashSet<>();
        posts.add(new Post());

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void view1() throws Exception {
//        mockMvc.perform(get("/posts/view/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("posts"))
//                .andExpect(view().name("posts/view"));
    }

    @Test
    void listUserPosts() throws Exception {

        when(postService.findAll()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("posts", postService.findAll()))
                .andExpect(view().name("posts/index"));

        assertEquals(1, postService.findAll().size());
    }

    @Test
    void createPost() throws Exception {
        mockMvc.perform(get("/posts/create"))
                .andExpect(model().attributeExists("post"))
                .andExpect(view().name("posts/create"));
    }

    @Test
    void showFormForUpdate() throws Exception {

        mockMvc.perform(get("/posts/showFormForUpdate?postId=1"))
                .andExpect(status().isOk())
//                .andExpect(model().attribute("post", post))
                .andExpect(view().name("posts/create"));
    }

    @Test
    void savePost() throws Exception {
//        mockMvc.perform(post("/posts/save"))
//                .andExpect(status().is3xxRedirection());
    }

    @Test
    void showFormForDelete() throws Exception {
        mockMvc.perform(get("/posts/delete?postId=1"))
                .andExpect(status().is3xxRedirection());
    }
}