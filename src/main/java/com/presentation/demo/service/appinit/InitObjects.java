package com.presentation.demo.service.appinit;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import com.presentation.demo.service.reset_password_token.ResetPasswordTokenService;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import static com.presentation.demo.constants.Params.*;
import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_ADMIN;

@Service
public class InitObjects implements CommandLineRunner {

    private static final Logger onStartLogger =
            LoggerFactory.getLogger(InitObjects.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;

    @Override
    public void run(String... args) throws Exception {
        onStartLogger.info("Application started...");
        User admin = userService.findUserByUsername(ADMIN_NAME);
        if (admin == null){

            onStartLogger.info("Admin not found. Creating admin...");

            MobilePhoneNumber adminMobilePhoneNumber = new MobilePhoneNumber();
            adminMobilePhoneNumber.setMobilePhoneNumberValue(ADMIN_MOBILE_PHONE_NUMBER);

            String adminPassword = userService.generateRandomPassword(RANDOM_PASSWORD_LENGTH);

            admin = new User();
            admin.setPassword(adminPassword);
            admin.setMobilePhoneNumber(adminMobilePhoneNumber);
            admin.setAuthority(ROLE_ADMIN);
            admin.setUsername(ADMIN_NAME);
            admin.setEmail(ADMIN_EMAIL);

            onStartLogger.info("SYSADMIN password:" + adminPassword);

            userService.save(admin);
            mobilePhoneNumberService.save(adminMobilePhoneNumber);

            resetPasswordTokenService.expireDateResetPasswordTokenAsyncDeleter();
        }
        else{
            onStartLogger.info("Admin found." + admin.getUsername());
        }
    }
}
