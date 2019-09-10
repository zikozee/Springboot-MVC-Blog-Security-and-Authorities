package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.Authority;
import com.zikozee.springboot.mvcblog.models.User;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AuthorityService authorityService;
    private NotificationService notifyService;
    private List<Authority> userAuthority;

    public AdminServiceImpl(AuthorityService authorityService, NotificationService notifyService) {
        this.authorityService = authorityService;
        this.notifyService = notifyService;
    }

    @Override
    public String CheckIfUserHasRole(User user, String chosenAuthority) {
        userAuthority = authorityService.findByUsername(user.getUsername());
        for (Authority authority : userAuthority) {
            if (authority.getUsername().equals("ROLE_" + chosenAuthority)) {
                notifyService.addInfoMessage("User Already has selected right");
                return "/users";
            }
        }
        return null;
    }

    @Override
    public void addNewRole(User user, String chosenAuthority) {
        userAuthority.add(new Authority(user.getUsername(), "ROLE_" + chosenAuthority));
        user.setAuthorities(userAuthority);
    }
}
