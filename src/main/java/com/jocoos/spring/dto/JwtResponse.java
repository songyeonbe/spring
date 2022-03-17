package com.jocoos.spring.dto;


import lombok.Getter;

@Getter
public class JwtResponse {
    private String token;
    private String username;

    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
