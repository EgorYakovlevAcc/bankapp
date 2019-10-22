package com.presentation.demo.service.user;

import com.presentation.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Integer id);
    void delete(User user);
    void save(User user);
    List<User> findAll();
}