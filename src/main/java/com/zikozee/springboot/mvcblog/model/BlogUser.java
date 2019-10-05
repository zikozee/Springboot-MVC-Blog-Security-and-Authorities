package com.zikozee.springboot.mvcblog.model;

import com.zikozee.springboot.mvcblog.validation.FieldMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
@Getter @Setter @NoArgsConstructor
public class BlogUser {
    @NotNull(message = "is required")
    @Size(min = 2, max = 30, message = "Username size should be in range [2...30]")
    private String username;

    @NotNull(message = "is required")
    @Size(min = 1, max = 50,message = "minimum of 1 character")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, max = 50, message = "minimum of 1 character")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 1, max = 50, message = "Username size should be in range [5...50]")
    private String fullName;

}
