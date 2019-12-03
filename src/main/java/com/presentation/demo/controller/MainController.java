package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.security.SecurityService;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/registration")
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
        String username = user.getUsername();
        userService.save(user);
        securityService.autologin(username, password);
        return "redirect:/index";
    }

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "delete success";
    }
}
