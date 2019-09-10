package com.zikozee.springboot.mvcblog.controllers;

import com.zikozee.springboot.mvcblog.services.ErrorService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandler implements ErrorController {//or you can just annotate with @ExceptionHandler

    private ErrorService errorService;

    public ErrorHandler(ErrorService errorService) {
        this.errorService = errorService;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Exception exc, Model model) {

        return errorService.handle_404_500_error(request, exc, model);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
