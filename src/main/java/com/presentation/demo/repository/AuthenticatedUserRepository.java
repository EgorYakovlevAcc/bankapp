package com.presentation.demo.repository;

import com.presentation.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticatedUserRepository extends JpaRepository<User,Long> {
    User findAuthenticatedUserById(Integer id);
    User findAuthenticatedUserByUsername(String username);
}
