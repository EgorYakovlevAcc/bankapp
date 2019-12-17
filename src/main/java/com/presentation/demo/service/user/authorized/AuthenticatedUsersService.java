package com.presentation.demo.service.user.authorized;

import com.presentation.demo.model.User;

import java.util.List;

public interface AuthenticatedUsersService {
    User findAuthenticatedUserByID(Long id);
    User findAuthenticatedUserByUsername(String username);
    void save(User user);
    void delete(User user);
    List<User> findAll();
}
