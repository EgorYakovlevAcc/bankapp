package com.presentation.demo.service.user;

import com.presentation.demo.constants.enums.AUTHORITIES;
import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Integer id);
    User findUserByUsername(String username);
    User findUserByMobilePhoneNumber(MobilePhoneNumber number);
    User findUserByEmail(String email);
    void delete(User user);
    void save(User user);
    List<User> findAll();
    String generateRandomPassword(Integer length);
}
