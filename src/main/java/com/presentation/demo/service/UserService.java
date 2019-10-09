package com.presentation.demo.service;

import com.presentation.demo.model.User;

public interface UserService {
    User findUserById(Integer id);
    void delete(User user);
    void save(User user);
}
