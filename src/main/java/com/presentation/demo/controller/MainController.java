package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
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

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/index")
    public String getIndex() {

       return "index";
   }

   @GetMapping("/login")//give data to server through forms
    public String getLogin(Model model){
        model.addAttribute("user",new User());
        return "login";
   }

    @GetMapping("/error")//give data to server through forms
    @ResponseBody
    public String getLogin(){
        return "error";
    }

   @GetMapping("/registration")
    public String getRegistration(Model model){
        model.addAttribute("user",new User());
        return "registration";
   }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

    @PostMapping("/login")
    public String printLogin(@ModelAttribute("user") User user, Model model) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return "redirect:about";
    }
//todo: userpage wants userid, so we have to give it to him after login

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user,Model model) throws Exception {
        String password = user.getPassword();
        String username = user.getUsername();
        userService.save(user);
        securityService.autoLogin(username,password);
        return "redirect:index";
    }

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "delete success";
    }
}
