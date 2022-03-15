package com.jocoos.spring.controller;

import com.jocoos.spring.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/hello")
    public String login() {
        String name = "로그인 테스트";
        log.info("hello={}", name);
        return "ok";
    }
}
