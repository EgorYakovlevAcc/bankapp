package com.presentation.demo.repository;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Long id);
    User findUserByUsername(String username);
    User findUserByMobilePhoneNumber(MobilePhoneNumber mobilePhoneNumber);
    User findUserByEmail(String email);
}
