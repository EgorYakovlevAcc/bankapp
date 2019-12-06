package com.presentation.demo.service.user;

import com.presentation.demo.constants.enums.ROLES;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.presentation.demo.constants.Constant.randomPasswordLength;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByRole(ROLES role) {
        return userRepository.findUserByRole(role.getName());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String generateRandomPassword(Integer length) {
        RandomValueStringGenerator randomValueStringGenerator = new RandomValueStringGenerator(length);
        randomValueStringGenerator.setRandom(new Random());
        return randomValueStringGenerator.generate();
    }
}
