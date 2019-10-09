package com.presentation.demo.service;

import com.presentation.demo.model.User;
import com.presentation.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void Save(User user) {
        userRepository.save(user);
    }

    public UserServiceImpl(){}
}
