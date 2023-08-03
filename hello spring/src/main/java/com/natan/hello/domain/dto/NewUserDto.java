package com.natan.hello.domain.dto;

import com.natan.hello.domain.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewUserDto {
    private String name;
    private String username;
    private String email;
}
