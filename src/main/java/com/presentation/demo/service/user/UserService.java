package com.presentation.demo.service.user;

import com.presentation.demo.model.User;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

public interface UserService {
    User findUserById(Integer id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    void delete(User user);
    void save(User user);
    List<User> findAll();
}
