package com.presentation.demo.service.appinit;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.presentation.demo.constants.Constant.*;
import static com.presentation.demo.constants.enums.AUTHORITIES.ADMIN;
import static com.presentation.demo.constants.enums.AUTHORITIES.USER;

@Service
public class InitObjects implements CommandLineRunner {

    private static final Logger onStartLogger =
            LoggerFactory.getLogger(InitObjects.class);

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        onStartLogger.info("Application started...");
        User admin = userService.findUserByUsername(ADMIN_NAME);
        if (admin == null){

            onStartLogger.info("Admin not found. Creating admin...");

            admin = new User();
            String adminPassword = userService.generateRandomPassword(RANDOM_PASSWORD_LENGTH);
            admin.setPassword(adminPassword);

            onStartLogger.info("SYSADMIN password:" + adminPassword);

            admin.setAuthority(ADMIN);
            admin.setUsername(ADMIN_NAME);
            admin.setEmail(ADMIN_EMAIL);

            MobilePhoneNumber adminMobilePhoneNumber = new MobilePhoneNumber();
            adminMobilePhoneNumber.setOwner(admin);
            adminMobilePhoneNumber.setMobilePhoneNumberValue(ADMIN_MOBILE_PHONE_NUMBER);

            userService.save(admin);
        }
        else{
            onStartLogger.info("Admin found." + admin.getUsername());
        }
    }
}
