package com.jocoos.spring.controller;

import com.jocoos.spring.domain.users.Users;
import com.jocoos.spring.dto.JwtResponse;
import com.jocoos.spring.dto.UsersRequestDto;
import com.jocoos.spring.service.UsersService;
import com.jocoos.spring.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersService usersService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UsersRequestDto requestDto) throws Exception {
        String username = requestDto.getUsername();
        String token = "test token";

        log.info("username={}", username);

        // db 저장
        usersService.create(requestDto);

        return ResponseEntity.ok().body(new JwtResponse(token, username));
    }

    @GetMapping("/test/permit-all")
    public String every() {
        log.info("hello everybody");
        return "ok";
    }

    @GetMapping("/test/user")
    public String user() {
        log.info("user allowed");
        return "ok";
    }
}
