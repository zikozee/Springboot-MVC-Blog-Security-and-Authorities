package com.zikozee.springboot.mvcblog.controllers;

import com.zikozee.springboot.mvcblog.model.Post;
import com.zikozee.springboot.mvcblog.model.User;
import com.zikozee.springboot.mvcblog.services.PostService;
import com.zikozee.springboot.mvcblog.services.UserService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserService userService;
    @Mock
    PostService postService;
    @InjectMocks
    UserController controller;

    MockMvc mockMvc;

    Set<User> users;

    Set<Post> userPosts;

    @BeforeEach
    void setUp() {
        userPosts = postService.findByAuthor("username");

        users = new HashSet<>();
        users.add(new User());

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }


    @Test
    void listUsers() throws Exception {
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", userService.findAll()))
                .andExpect(view().name("users/index"));

    }

    @Test
    void listUserPost() throws Exception {

        when(postService.findByAuthor(anyString())).thenReturn(userPosts);

        mockMvc.perform(get("/users/post"))
                .andExpect(status().isOk());

    }

    @Test
    void showFormForDelete() throws Exception {
        mockMvc.perform(get("/users/delete?userId=1"))
                .andExpect(status().is3xxRedirection());
    }
}