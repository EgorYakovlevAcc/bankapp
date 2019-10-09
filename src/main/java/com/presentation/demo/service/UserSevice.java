package com.presentation.demo.service;

import com.presentation.demo.model.User;

public interface UserSevice {
    User findUserById(Integer id);
    void save(User user);
    void delete(User user);
}
