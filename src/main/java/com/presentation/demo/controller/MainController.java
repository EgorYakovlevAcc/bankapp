package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.presentation.demo.constants.enums.ROLES.USER;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = {"/index", "/"})
    public String getIndex(Model model, @AuthenticationPrincipal User user) {
        String userName = Objects.nonNull(user) ? user.getUsername() : "none";
        System.out.println("Here you can see your name: " + userName);
        model.addAttribute("user_name", userName);
        return "index";
    }

    @GetMapping("/error")
    @ResponseBody
    public String getError() {
        return "error";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user) throws Exception {
        String password = user.getPassword();
        String username = user.getUsername();
        user.setRole(USER);
        userService.save(user);
        securityService.autoLogin(username, password);
        return "redirect:index";
    }

    //give data to server through forms
    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String setLogin(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return "redirect:/userpage";
    }

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "Delete success!";
    }

}
