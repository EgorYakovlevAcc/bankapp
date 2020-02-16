package com.presentation.demo.service.appinit;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.service.mail.MailSendingService;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import com.presentation.demo.service.reset_password_token.ResetPasswordTokenService;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import static com.presentation.demo.constants.Properties.RANDOM_PASSWORD_LENGTH;
import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_ADMIN;

@Service
public class InitObjects implements CommandLineRunner {

    private static final Logger onStartLogger =
            LoggerFactory.getLogger(InitObjects.class);

    @Value("${spring.security.admin.email}")
    private String adminEmail;

    @Value("${spring.security.admin.name}")
    private String adminName;

    @Value("${spring.security.admin.phone}")
    private String adminPhone;

    @Autowired
    private UserService userService;

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    private MailSendingService mailSendingService;

    @Override
    public void run(String... args) throws Exception {
        onStartLogger.info("Application started...");
        User admin = userService.findUserByUsername(adminName);
        if (admin == null){

            onStartLogger.info("Admin not found. Creating admin...");

            MobilePhoneNumber adminMobilePhoneNumber = new MobilePhoneNumber();
            adminMobilePhoneNumber.setMobilePhoneNumberValue(adminPhone);

            String adminPassword = userService.generateRandomPassword(RANDOM_PASSWORD_LENGTH);

            admin = new User();
            admin.setPassword(adminPassword);
            admin.setMobilePhoneNumber(adminMobilePhoneNumber);
            admin.setRole(ROLE_ADMIN);
            admin.setUsername(adminName);
            admin.setEmail(adminEmail);

            onStartLogger.info("SYSADMIN password:" + adminPassword);

            userService.save(admin);
            mobilePhoneNumberService.save(adminMobilePhoneNumber);
            mailSendingService.sendSimple(adminEmail,"Password set.",String.format("Your administrator password is set to %s. \n" +
                    "\n" + "Regards, NCBank team.",adminPassword));
        }
        else{

            onStartLogger.info("Admin found." + admin.getUsername());
        }
    }
}
