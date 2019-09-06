package com.zikozee.springboot.mvcblog.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandler implements ErrorController {//or you can just annotate with @ExceptionHandler

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Exception exc, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("message", exc.getMessage());
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("message", exc.getMessage());
                return "error/500";
            }
        }
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
