package com.zikozee.springboot.mvcblog.services;

import javax.servlet.http.HttpServletRequest;

public interface ErrorService {
    String handle_404_500_error(HttpServletRequest request, Exception exc);
}
