package com.presentation.demo.service.reset_password_token;

import com.presentation.demo.model.ResetPasswordToken;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.ResetPasswordTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import static com.presentation.demo.constants.Params.ASYNC_TOKEN_VALIDITY_CHECKING_DELTA_TIME;
import static com.presentation.demo.constants.Params.RESET_TOKEN_VALIDITY_HOURS;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    private Logger RESET_TOKEN_SERVICE_LOGGER = LoggerFactory.getLogger(ResetPasswordTokenServiceImpl.class);

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Override
    public void save(ResetPasswordToken resetPasswordToken) {
        resetPasswordTokenRepository.save(resetPasswordToken);
    }

    @Override
    public void delete(ResetPasswordToken resetPasswordToken) {
        resetPasswordTokenRepository.delete(resetPasswordToken);
    }

    @Override
    public ResetPasswordToken findResetPasswordTokenById(Integer id) {
        return resetPasswordTokenRepository.findResetPasswordTokenById(id);
    }

    @Override
    public ResetPasswordToken findResetPasswordTokenByUser(User user) {
        return resetPasswordTokenRepository.findResetPasswordTokenByUser(user);
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
    @Async("asyncExecutor")
    @Transactional
    public void expireDateResetPasswordTokenAsyncDeleter() {
        while(true){
            Calendar upperLimitCheckTime = Calendar.getInstance();
            upperLimitCheckTime.add(Calendar.HOUR,ASYNC_TOKEN_VALIDITY_CHECKING_DELTA_TIME);
            List<ResetPasswordToken> expiredTokens = resetPasswordTokenRepository.findResetPasswordTokensByExpireDateBefore(upperLimitCheckTime);
            for(ResetPasswordToken expiredToken: expiredTokens){
                resetPasswordTokenRepository.delete(expiredToken);
            }
            try{
                Thread.sleep(ASYNC_TOKEN_VALIDITY_CHECKING_DELTA_TIME);
            }
            catch (InterruptedException interruptedExc){
                RESET_TOKEN_SERVICE_LOGGER.info(interruptedExc.getMessage());
            }
        }
    }

    @Override
    public void createResetPasswordToken(String token, User user) {
        RESET_TOKEN_SERVICE_LOGGER.info("TOKEN: " + token);
        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.HOUR,RESET_TOKEN_VALIDITY_HOURS);

        ResetPasswordToken oldToken = findResetPasswordTokenByUser(user);
        if (oldToken == null){
            ResetPasswordToken resetPasswordToken = new ResetPasswordToken();

            resetPasswordToken.setUser(user);
            resetPasswordToken.setToken(token);
            resetPasswordToken.setExpireDate(expireDate);
            resetPasswordTokenRepository.save(resetPasswordToken);
        }
        else{
            oldToken.setToken(token);
            oldToken.setExpireDate(expireDate);
            resetPasswordTokenRepository.save(oldToken);
        }
    }

}
