package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.AdmPassTimer.AdmPassTimerServiceImpl;
import com.presentation.demo.service.security.SecurityService;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdmPassTimerServiceImpl admPassTimerService;

    @Autowired
    private UserService userService;

    private Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping(value = {"/", "/index"})
    public String getIndex() {
        return "index";
    }

    @GetMapping(value = {"/login", "/login.html"})
    public String getLogin(Model model, String error, String logout) {
        System.out.println("get login mapping");
        model.addAttribute("user", new User());
        if (error != null) {
            System.out.println("error login");
            model.addAttribute("loginError", "Error with username or password");
        } else if (logout != null) {
            System.out.println("logout");
            model.addAttribute("loginError", "Successful logout");
        } else {
            System.out.println("login new user");
        }
        return "login";
    }

    @GetMapping(value = {"/registration", "registartion.html"})
    public String getRegistration(Model model) {
        model.addAttribute("user", new User());
        return ("registration");
    }

    @GetMapping("/about")
    public String about() {
        return ("about");
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute("user") User user) {
        System.out.println("post login mapping");
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        LOGGER.debug("login: user = {}", user.getUsername());
//        int userId = user.getId();
//        return "redirect:userpage?userid={userId}";
        return "redirect:/userpage";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user,
                               Model model,
                               HttpServletRequest request) throws Exception {
        System.out.println("AGAIN REGISTRATION");
        String password = user.getPassword();
        String email = user.getEmail();
        String username = user.getUsername();
        userService.save(user);
        securityService.autologin(username, password);
//        TODO: FIX EMAIL SENDING
//        try (GenericXmlApplicationContext context = new GenericXmlApplicationContext()) {
//            context.load("classpath:applicationContext.xml");
//            context.refresh();
//            JavaMailSender mailSender = context.getBean("mailSender", JavaMailSender.class);
//            SimpleMailMessage templateMessage = context.getBean("templateMessage", SimpleMailMessage.class);
//
//            // Создаём потокобезопасную копию шаблона.
//            SimpleMailMessage mailMessage = new SimpleMailMessage(templateMessage);
//
//            //TODO: Сюда напишите свой e-mail получателя.
//            mailMessage.setTo(email);
//
//            mailMessage.setText("Привет, товарищи. Присылаю вам письмо...");
//            try {
//                mailSender.send(mailMessage);
//                System.out.println("Mail sended");
//            } catch (MailException mailException) {
//                System.out.println("Mail send failed.");
//                mailException.printStackTrace();
//            }
//        } catch (Exception e){
//            e.getMessage();
//        }
        return "redirect:/index";
    }

    @GetMapping("/getadminpass")
    @ResponseBody
    public String adminPass() {
        return admPassTimerService.getAdminPassword() + "\n" + admPassTimerService.getLifetime();
    }

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "delete success";
    }
}
