package com.presentation.demo.repository;

import com.presentation.demo.model.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken,Integer> {
    ResetPasswordToken findResetPasswordTokenById(Integer id);
    ResetPasswordToken findResetPasswordTokenByToken(String token);
    List<ResetPasswordToken> findResetPasswordTokensByExpireDate(Calendar expireDate);
    List<ResetPasswordToken> findResetPasswordTokensByExpireDateBefore(Calendar date);
    List<ResetPasswordToken> findResetPasswordTokensByExpireDateAfter(Calendar date);
}
