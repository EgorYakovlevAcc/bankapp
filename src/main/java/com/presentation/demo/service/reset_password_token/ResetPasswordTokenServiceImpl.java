package com.presentation.demo.service.reset_password_token;

import com.presentation.demo.model.ResetPasswordToken;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.ResetPasswordTokenRepository;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import static com.presentation.demo.constants.Properties.FIXED_DELAY_MILLISECONDS;
import static com.presentation.demo.constants.Properties.INITIAL_DELAY_MILLISECONDS;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    private Logger RESET_TOKEN_SERVICE_LOGGER = LoggerFactory.getLogger(ResetPasswordTokenServiceImpl.class);

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private UserService userService;

    @Override
    public void save(ResetPasswordToken resetPasswordToken) {
        resetPasswordTokenRepository.save(resetPasswordToken);
    }

    @Transactional
    @Override
    public void delete(ResetPasswordToken resetPasswordToken) {
        resetPasswordTokenRepository.delete(resetPasswordToken);
    }

    @Override
    public ResetPasswordToken findResetPasswordTokenById(Integer id) {
        return resetPasswordTokenRepository.findResetPasswordTokenById(id);
    }

    @Override
    public ResetPasswordToken findResetPasswordTokenByToken(String token) {
        return resetPasswordTokenRepository.findResetPasswordTokenByToken(token);
    }

    @Override
    @Transactional
    public List<ResetPasswordToken> findResetPasswordTokensByExpireDate(Calendar expireDate) {
        return resetPasswordTokenRepository.findResetPasswordTokensByExpireDate(expireDate);
    }

    @Override
    @Transactional
    public List<ResetPasswordToken> findResetPasswordTokensByExpireDateBefore(Calendar date) {
        return resetPasswordTokenRepository.findResetPasswordTokensByExpireDateBefore(date);
    }

    @Override
    public List<ResetPasswordToken> findResetPasswordTokensByExpireDateAfter(Calendar date) {
        return resetPasswordTokenRepository.findResetPasswordTokensByExpireDateAfter(date);
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = FIXED_DELAY_MILLISECONDS,initialDelay = INITIAL_DELAY_MILLISECONDS)
    @Async("asyncExecutor")
    public void expireDateResetPasswordTokenAsyncDeleter() {
        RESET_TOKEN_SERVICE_LOGGER.info("ResetPasswordToken async checker is working...");
        Calendar upperLimitCheckTime = Calendar.getInstance();

        for(User user: userService.findAllByResetPasswordTokenNotNull()){
            if (user.getResetPasswordToken().getExpireDate().before(upperLimitCheckTime)){
                user.setResetPasswordToken(null);
                userService.save(user);
            }
        }
    }

    @Override
    public void createResetPasswordToken(String token, User user) {
        RESET_TOKEN_SERVICE_LOGGER.info("TOKEN: " + token);
        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.SECOND,5);

        ResetPasswordToken oldToken = user.getResetPasswordToken();
        if (oldToken == null){
            ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
            resetPasswordToken.setToken(token);
            resetPasswordToken.setExpireDate(expireDate);

            user.setResetPasswordToken(resetPasswordToken);
            resetPasswordTokenRepository.save(resetPasswordToken);
            userService.save(user);
        }
        else{
            oldToken.setToken(token);
            oldToken.setExpireDate(expireDate);
            resetPasswordTokenRepository.save(oldToken);
        }
    }

}
