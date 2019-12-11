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

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String getAdminPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "/admin/admin";
    }

    @GetMapping("/admin/showpassword")
    @ResponseBody
    public String showPassword(@AuthenticationPrincipal User user){
        return user.getPassword();//comes from another service
    }


}
