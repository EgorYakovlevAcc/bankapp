package com.presentation.demo.service.user;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.ResetPasswordToken;
import com.presentation.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Integer id);
    User findUserByUsername(String username);
    User findUserByMobilePhoneNumber(MobilePhoneNumber number);
    User findUserByEmail(String email);
    User findUserByActivationCode(String code);
    User findUserByResetPasswordToken(ResetPasswordToken resetPasswordToken);
    List<User> findAllByResetPasswordTokenNotNull();
    List<User> findUsersByRoleEquals(String role);
    void delete(User user);
    void save(User user);
    List<User> findAll();
    void adminRandomPasswordGenerator();
    String generateRandomPassword(Integer length);
    void createUserWithActivationCode(User user);
    String activateUser(String activationCode);
    void processUserPasswordReset(String randomToken, User targetUser);
}
