package com.jocoos.spring.service;

import com.jocoos.spring.domain.users.UserRole;
import com.jocoos.spring.domain.users.Users;
import com.jocoos.spring.dto.UsersRequestDto;
import com.jocoos.spring.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users create(UsersRequestDto requestDto) {
        String username = requestDto.getUsername();
        Users existUser = usersRepository.findByUsername(username);

        if(existUser != null) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        String pw = passwordEncoder.encode(requestDto.getPassword());
        UserRole role = UserRole.USER;

        Users saveUsers = new Users(username, pw , role);

        return usersRepository.save(saveUsers);
    }


}
