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

    public Optional<Users> findByIdPw(String id) {
        return usersRepository.findById(id);
    }

}
