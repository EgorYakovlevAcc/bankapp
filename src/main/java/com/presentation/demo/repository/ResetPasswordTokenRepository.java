package com.presentation.demo.repository;

import com.presentation.demo.model.ResetPasswordToken;
import com.presentation.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.spi.CalendarDataProvider;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken,Integer> {
    ResetPasswordToken findResetPasswordTokenById(Integer id);
    ResetPasswordToken findResetPasswordTokenByUser(User user);
    ResetPasswordToken findResetPasswordTokenByToken(String token);
    List<ResetPasswordToken> findResetPasswordTokensByExpireDate(Calendar expireDate);
    List<ResetPasswordToken> findResetPasswordTokensByExpireDateBefore(Calendar date);
}
