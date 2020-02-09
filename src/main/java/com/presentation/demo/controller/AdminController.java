package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getAdminPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "/admin/admin";
    }

    @GetMapping("/showpassword")
    @ResponseBody
    public String showPassword(@AuthenticationPrincipal User user){
        return user.getPassword();//comes from another service
    }


}
