package com.presentation.demo.service.user;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private Logger SERVICE_LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackForClassName = "ConstraintViolationException")
    @Override
    public void save(User user) throws PersistenceException {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            userRepository.flush();
        }
        catch (ConstraintViolationException exp){
            SERVICE_LOGGER.info(exp.getLocalizedMessage());
            StringBuilder constraintViolationsInfo = new StringBuilder();
            for(ConstraintViolation<?> constraintViolation: exp.getConstraintViolations()){
                constraintViolationsInfo.append(constraintViolation.getMessage());
            }
            throw new PersistenceException(constraintViolationsInfo.toString());
        }
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByMobilePhoneNumber(MobilePhoneNumber mobilePhoneNumber) {
        return userRepository.findUserByMobilePhoneNumber(mobilePhoneNumber);
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
