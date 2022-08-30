package com.project.creditmanagement.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
public class UserLoginDTO implements Serializable {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}