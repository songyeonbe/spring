package com.jocoos.spring.repository;

import com.jocoos.spring.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {

}
