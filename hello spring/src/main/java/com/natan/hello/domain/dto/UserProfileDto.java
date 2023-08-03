package com.natan.hello.domain.dto;

import com.natan.hello.domain.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserProfileDto {
    private Long id;
    private String name;
    private String username;

    public UserProfileDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
    }
}
