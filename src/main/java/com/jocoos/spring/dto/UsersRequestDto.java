package com.jocoos.spring.dto;

import com.jocoos.spring.domain.users.UserRole;
import com.jocoos.spring.domain.users.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsersRequestDto {

    private String username;
    private String password;
    private String name;

    @Builder
    public UsersRequestDto(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

}
