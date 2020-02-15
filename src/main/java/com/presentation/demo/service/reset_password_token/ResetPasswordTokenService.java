package com.presentation.demo.service.reset_password_token;

import com.presentation.demo.model.ResetPasswordToken;
import com.presentation.demo.model.User;

import java.util.Calendar;
import java.util.List;

public interface ResetPasswordTokenService {
    void save(ResetPasswordToken resetPasswordToken);
    void delete(ResetPasswordToken resetPasswordToken);
    ResetPasswordToken findResetPasswordTokenById(Integer id);
    ResetPasswordToken findResetPasswordTokenByUser(User user);
    ResetPasswordToken findResetPasswordTokenByToken(String token);
    List<ResetPasswordToken> findResetPasswordTokensByExpireDate(Calendar expireDate);
    List<ResetPasswordToken> findResetPasswordTokensByExpireDateBefore(Calendar date);
    void expireDateResetPasswordTokenAsyncDeleter();
    void createResetPasswordToken(String token, User user);
}
