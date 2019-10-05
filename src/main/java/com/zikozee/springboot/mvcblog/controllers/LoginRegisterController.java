package com.zikozee.springboot.mvcblog.controllers;

import com.zikozee.springboot.mvcblog.model.BlogUser;
import com.zikozee.springboot.mvcblog.model.LoginForm;
import com.zikozee.springboot.mvcblog.model.User;
import com.zikozee.springboot.mvcblog.services.NotificationService;
import com.zikozee.springboot.mvcblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginRegisterController {

    private UserService userService;
    private NotificationService notifyService;

    @Autowired
    public LoginRegisterController(UserService theUserService, NotificationService theNotifyService, PasswordEncoder passwordEncoder) {
        this.userService = theUserService;
        this.notifyService = theNotifyService;
    }

    @GetMapping("/showMyLoginPage")
    public String Login(LoginForm loginForm) {
        return "users/login";
    }

    @PostMapping("/users/login")
    public String LoginPage(@Valid LoginForm loginForm, BindingResult theBinBindingResult, Model model){
        if(theBinBindingResult.hasErrors()){
            notifyService.addErrorMessage("Please fill form correctly");
            return "users/login";
        }

        notifyService.addInfoMessage("Login successful");
        model.addAttribute("loginForm", loginForm);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getName());
        return "redirect:/";
    }

    @GetMapping("/users/register")
    public String register(Model theModel){

        theModel.addAttribute("blogUser", new BlogUser());

        return "users/register";
    }

    @PostMapping("/users/register")
    public String RegistrationPage(@Valid BlogUser blogUser,
                                   BindingResult theBinBindingResult, Model model){
        if(theBinBindingResult.hasErrors()){
            notifyService.addErrorMessage("Please fill form correctly");
            return "users/register";
        }
//
        if(userService.findByUserName(blogUser.getUsername()) == null){
          User user = userService.create(blogUser);
        }else{
            notifyService.addErrorMessage(blogUser.getUsername() +" Already in use");
            return "users/register";
        }

        notifyService.addInfoMessage("Registration successful");//should this be there since am redirecting to users Login
        model.addAttribute("blogUser", blogUser);
        return "redirect:/";
        //instead of directing to login page, direct to homepage and since you must be logged in to view homepage,
        //You are Auto-directed to Login Page
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {//can refactor to be in userService
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/showMyLoginPage?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

}
