package com.zikozee.springboot.mvcblog.controllers;

import com.zikozee.springboot.mvcblog.services.ErrorService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandler implements ErrorController {//or you can just annotate with @ExceptionHandler

    private ErrorService errorService;

    public ErrorHandler(ErrorService errorService) {
        this.errorService = errorService;
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Exception exc) {

        return errorService.handle_404_500_error(request, exc);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
