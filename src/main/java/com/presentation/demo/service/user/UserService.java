package com.presentation.demo.service.user;

import com.presentation.demo.constants.enums.AUTHORITIES;
import com.presentation.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);
    User findUserByUsername(String username);
    User findUserByAuthority(AUTHORITIES authority);
    void delete(User user);
    void save(User user);
    List<User> findAll();
    String generateRandomPassword(Integer length);
    User findUserByEmail(String email);
}
