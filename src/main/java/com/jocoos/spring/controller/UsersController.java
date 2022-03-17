package com.jocoos.spring.controller;

import com.jocoos.spring.config.security.CustomUserDetailsService;
import com.jocoos.spring.config.security.SecurityUser;
import com.jocoos.spring.dto.JwtResponse;
import com.jocoos.spring.dto.UsersRequestDto;
import com.jocoos.spring.service.UsersService;
import com.jocoos.spring.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersService usersService;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UsersRequestDto requestDto) throws Exception {
        String username = requestDto.getUsername();
        log.info("username={}", username);

        usersService.create(requestDto);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.createToken(userDetails);
        log.info("토큰 발급 {}", token);

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
