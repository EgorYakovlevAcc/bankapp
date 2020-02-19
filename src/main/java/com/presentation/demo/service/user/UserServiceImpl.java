package com.presentation.demo.service.user;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.ResetPasswordToken;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.MobilePhoneNumberRepository;
import com.presentation.demo.repository.UserRepository;
import com.presentation.demo.service.mail.MailSendingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.presentation.demo.constants.Properties.*;
import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {


    private static final Logger USER_SERVICE_LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String CHANGE_PASSWORD_FORM_PATH =  "templates/changePassword";

    @Value("${spring.security.admin.name}")
    private String adminName;

    @Value("${spring.security.admin.email}")
    private String adminEmail;

    @Value("${server.port}")
    private Integer serverPort;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MobilePhoneNumberRepository mobilePhoneNumberRepository;

    @Autowired
    private MailSendingService mailSendingService;

    @Override
    public void save(User user) throws PersistenceException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
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
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByActivationCode(String code) {
        return userRepository.findUserByActivationCode(code);
    }


    @Override
    public User findUserByResetPasswordToken(ResetPasswordToken resetPasswordToken) {
        return userRepository.findUserByResetPasswordToken(resetPasswordToken);
    }

    @Override
    public List<User> findAllByResetPasswordTokenNotNull() {
        return userRepository.findAllByResetPasswordTokenNotNull();
    }

    @Override
    public List<User> findUsersByRoleEquals(String role) {
        return userRepository.findUsersByRoleEquals(role);
    }


    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }


    private String getIp(){
        try{
            return InetAddress.getLocalHost().getCanonicalHostName();
        }
        catch (UnknownHostException unkExc){
            USER_SERVICE_LOGGER.info(unkExc.getMessage());
        }
        return null;
    }

    @Override
    @Async("asyncExecutor")
    @Scheduled(fixedDelay = ADMIN_PASSWORD_UPDATE_FREQUENCY_MILLISECONDS,initialDelay = ADMIN_PASSWORD_UPDATE_FREQUENCY_MILLISECONDS)
    public void adminRandomPasswordGenerator() {
        String newRandomPassword = generateRandomPassword(RANDOM_PASSWORD_LENGTH);

        User admin = userRepository.findUserByUsername(adminName);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        userRepository.save(admin);

        mailSendingService.sendSimple(adminEmail,"Password changing.",String.format("Your administrator password has changed to %s. \n" +
                "\n" + "Regards, NCBank team.",newRandomPassword));
    }

    @Override
    public String generateRandomPassword(Integer length){
        RandomValueStringGenerator randomValueStringGenerator = new RandomValueStringGenerator(length);
        randomValueStringGenerator.setRandom(new Random());
        return randomValueStringGenerator.generate();
    }

    @Override
    public void createUserWithActivationCode(User user) {
        user.setRole(ROLE_USER);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String activationMessage = String.format(" Hello, %s! \n\n " +
                        "Welcome to NCBank! To activate your account please visit: %s:%s/activate/%s. " +
                        "Activation allows you to change account's password if you have forgotten it. \n\n " +
                        "Regards, NCBank team.",
                user.getUsername(), getIp(), serverPort, user.getActivationCode());
        mailSendingService.sendSimple(user.getEmail(),"Activation code.",activationMessage);
        userRepository.save(user);
    }

    @Override
    public String activateUser(String activationCode) {
        User user = userRepository.findUserByActivationCode(activationCode);
        if (user == null){
            return String.format("User with code \"%s\" has already been activated " +
                    " or this activation code is not valid.",activationCode);
        }
        else{
            user.setActivationCode(null);
            userRepository.save(user);
            return String.format("User \"%s\" has successfully been activated",user.getUsername());
        }
    }

    @Override
    public void processUserPasswordReset(String targetUserToken, User targetUser) {
        String link = String.format("%s:%s/password/change%s", getIp(), serverPort, "?user_id=" + targetUser.getId() + "&reset_token=" + targetUserToken);
        String resetMessage = String.format(" Hello, %s! \n\n " +
                        "Welcome to NCBank! To reset your account password visit: %s. " +
                        "This link would be available for " + RESET_TOKEN_VALIDITY_HOURS + " hours so if you did't make a request ignore it. \n\n " +
                        "Regards, NCBank team.", targetUser.getUsername(), link);
        mailSendingService.sendSimple(targetUser.getEmail(),"Password reset.", resetMessage);
//        Doesn't work yet

//        HashMap<String, Object> modelAttributes = new HashMap<>();
//        modelAttributes.put("user_id", targetUser.getId());
//        modelAttributes.put("reset_token",targetUserToken);
//        modelAttributes.put("password",new StringWrapper());
//        mailSendingService.sendMime(targetUser.getEmail(),"Password reset.", CHANGE_PASSWORD_FORM_PATH, modelAttributes);
    }
}
