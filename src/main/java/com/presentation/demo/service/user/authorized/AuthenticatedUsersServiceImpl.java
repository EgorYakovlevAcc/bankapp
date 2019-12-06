package com.presentation.demo.service.user.authorized;

import com.presentation.demo.model.User;
import com.presentation.demo.repository.AuthenticatedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticatedUsersServiceImpl implements AuthenticatedUsersService {

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Override
    public User findAuthenticatedUserByID(Long id) {
        return authenticatedUserRepository.findAuthenticatedUserById(id);
    }

    @Override
    public User findAuthenticatedUserByUsername(String username) {
        return authenticatedUserRepository.findAuthenticatedUserByUsername(username);
    }

    @Override
    public void save(User user) {
        authenticatedUserRepository.save(user);
    }

    @Override
    public void delete(User user) {
        authenticatedUserRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return authenticatedUserRepository.findAll();
    }
}
