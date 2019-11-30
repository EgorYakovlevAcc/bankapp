package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @GetMapping("/error")
    @ResponseBody
    public String getError(){
        return "error";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

   @GetMapping("/registration")
    public String getRegistration(Model model){
        model.addAttribute("user",new User());
        return "registration";
   }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user) throws Exception {
        String password = user.getPassword();
        String username = user.getUsername();
        userService.save(user);
        securityService.autoLogin(username,password);
        return "redirect:index";
    }

    @GetMapping("/login")//give data to server through forms
    public String getLogin(Model model){
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentAuthenticatedUser = (User)authentication.getPrincipal();

        if (currentAuthenticatedUser.equals(user)){
            return "redirect:about";
        }
        else{
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return "redirect:index";
        }
    }
//  todo: userpage wants userid, so we have to give it to him after login

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "Delete success!";
    }

}
