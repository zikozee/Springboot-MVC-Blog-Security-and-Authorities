package com.zikozee.springboot.mvcblog.services;

public interface NotificationService {
    void addInfoMessage(String msg);
    void addErrorMessage(String msg);

}
