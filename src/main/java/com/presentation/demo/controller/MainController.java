package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.security.SecurityService;
import com.presentation.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/index"})
    public String getIndex() {

       return "index";
   }

   @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("user", new User());
        return "login";
   }

   @GetMapping("/registration")
    public String getRegistration(Model model){
        model.addAttribute("user", new User());
        return ("registration");
   }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return "redirect:userpage";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user,
                               Model model,
                               HttpServletRequest request) throws Exception {
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
