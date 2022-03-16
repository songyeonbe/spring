package com.jocoos.spring.service;

import com.jocoos.spring.domain.users.Users;
import com.jocoos.spring.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public Users create(Users users) {
        // Todo 유효성 검사
        // Todo dto toEntity 사용하기
        return usersRepository.save(users);
    }


}
