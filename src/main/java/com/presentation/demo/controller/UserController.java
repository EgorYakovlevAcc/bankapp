package com.presentation.demo.controller;

import com.presentation.demo.model.ResetPasswordToken;
import com.presentation.demo.model.User;
import com.presentation.demo.pojo.StringWrapper;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import com.presentation.demo.service.datebalancehistory.DateBalanceHistoryService;
import com.presentation.demo.service.mail.MailSendingService;
import com.presentation.demo.service.reset_password_token.ResetPasswordTokenService;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

import static com.presentation.demo.constants.Properties.DEFAULT_TEMPORARY_PASSWORD_FOR_RESET;
import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_USER;

@Controller
public class UserController {

    @Value(value = "${spring.security.admin.name}")
    private String adminName;

    @Autowired
    private UserService userService;

    @Autowired
    private DateBalanceHistoryService dateBalanceHistoryService;

    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    private MailSendingService mailSendingService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BillService billService;

    @Autowired
    private CardService cardService;

    private Logger USER_CONTROLLER_LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/createuser")
    @ResponseBody
    public String createUser(){
        User user = new User();
        Random rand = new Random();
        user.setEmail("a@a"+ Math.abs(rand.nextInt()) +".com");
        user.setUsername("A" + Math.abs(rand.nextInt()));
        user.setPassword("123");
        user.setPasswordConfirmation("123");
        user.setRole(ROLE_USER);
        userService.save(user);
        return user.getId().toString();
    }

    @GetMapping("/allusers")
    public String getAllUsers(Model model){
        model.addAttribute("users",userService.findAll());
        return "allusers";
    }

    @GetMapping(value = {"/userpage"})
    public String getUserPage(@AuthenticationPrincipal User principal, Model model) {
        if (USER_CONTROLLER_LOGGER.isDebugEnabled()) {
            USER_CONTROLLER_LOGGER.debug("getUserPage: user = {}", principal);
        }
        model.addAttribute("user", principal);
        model.addAttribute("bills", billService.findBillsByHolder(principal));
        model.addAttribute("cards", cardService.findCardsByCartHolder(principal));
        return "userpage";
    }


    @GetMapping(value = "/password/reset")
    public String getResetPasswordPage(Model model){
        model.addAttribute("email", new StringWrapper());
        return "resetPassword";
    }

    @PostMapping(value = "/password/reset")
    public String postResetPasswordPage(@ModelAttribute("email") StringWrapper email, Model model){
        User targetUser = userService.findUserByEmail(email.getTarget());
        if (targetUser == null){
            model.addAttribute("message","User with current email does't exist!");
            return "resetPassword";
        }
        else if (targetUser.getActivationCode() != null) {
            model.addAttribute("message","User is not activated! Operation is not permitted!");
            return "resetPassword";
        }
        else if (targetUser.getUsername().equals(adminName)){
            model.addAttribute("message","Can't change administrator's password manually!");
            return "resetPassword";
        }
        else{
            String randomToken = UUID.randomUUID().toString();
            resetPasswordTokenService.createResetPasswordToken(randomToken,targetUser);
            userService.processUserPasswordReset(randomToken,targetUser);
            model.addAttribute("message",String.format("We've sent message with link to complete your password reset on mail: \"%s\". Please, check it.",email));
            return "shortMessageTemplate";
        }
    }

    @GetMapping(value = "/password/change")
    public String getChangePasswordPage(Model model, @RequestParam("user_id") Integer id, @RequestParam("reset_token") String token){
        ResetPasswordToken resetPasswordToken = resetPasswordTokenService.findResetPasswordTokenByToken(token);
        if (resetPasswordToken == null){
            model.addAttribute("email",new StringWrapper());
            model.addAttribute("message","Your password reset token has expired!");
            return "resetPassword";
        }
        else if (!resetPasswordToken.getUser().getId().equals(id)){
            model.addAttribute("email",new StringWrapper());
            model.addAttribute("message","This user do not have reset password token!");
            return "resetPassword";
        }
        else{
            User user = userService.findUserById(id);
            USER_CONTROLLER_LOGGER.info(resetPasswordToken.getUser().getUsername());
            USER_CONTROLLER_LOGGER.info(user.getUsername());

            securityService.resetUserPassword(user, DEFAULT_TEMPORARY_PASSWORD_FOR_RESET);
            securityService.autoLogin(user.getUsername(),DEFAULT_TEMPORARY_PASSWORD_FOR_RESET, user.getAuthorities());
            model.addAttribute("password", new StringWrapper());
            return "changePassword";
        }
    }

    @PostMapping(value = "/password/change")
    public String postUpdatePassword(HttpServletRequest request, HttpServletResponse response, @ModelAttribute StringWrapper newPassword, @AuthenticationPrincipal User user, Model model){
        securityService.resetUserPassword(user, newPassword.getTarget());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }

        model.addAttribute("message","Your password has successfully been changed.");
        return "shortMessageTemplate";
    }

}
