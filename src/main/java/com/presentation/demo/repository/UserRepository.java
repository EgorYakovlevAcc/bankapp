package com.presentation.demo.repository;

import com.presentation.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Long id);
    User findUserByUsername(String username);
    User findUserByAuthority(String authority);

    User findUserByEmail(String email);
}
