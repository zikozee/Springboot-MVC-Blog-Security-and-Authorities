package com.zikozee.springboot.mvcblog.services;

import com.zikozee.springboot.mvcblog.models.User;

public interface AdminService {

    String CheckIfUserHasRole(User user, String authority);

    void addNewRole(User user, String authority);

}
