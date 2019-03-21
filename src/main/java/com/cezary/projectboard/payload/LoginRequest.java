package com.cezary.projectboard.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "username can not be blank")
    private String username;
    @NotBlank(message = "password can not be blank")
    private String password;
}
