package com.jocoos.spring.service;

import com.jocoos.spring.domain.users.UserRole;
import com.jocoos.spring.domain.users.Users;
import com.jocoos.spring.dto.UsersRequestDto;
import com.jocoos.spring.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users create(UsersRequestDto requestDto) {

        String username = requestDto.getUsername();
        log.info("서비스단 잘 동작 {}", username);

        String pw = passwordEncoder.encode(requestDto.getPassword());
        UserRole role = UserRole.USER;

        Users saveUsers = new Users(username, pw , role);

        return usersRepository.save(saveUsers);
    }


}
