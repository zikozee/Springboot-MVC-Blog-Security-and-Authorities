package com.zikozee.springboot.mvcblog.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class LoginForm {
    @Size(min = 2, max = 30, message = "Username size should be in range [2...30]")
    private String username;

    @NotNull @Size(min = 1, max = 50)
    private String password;
}
