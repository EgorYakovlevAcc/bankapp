package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    Random rnd=new Random();

    @GetMapping("/createuser")
    @ResponseBody
    public String createUser(){
        User user=new User();
        int s=rnd.nextInt();
        Math.abs(s);
        user.setEmail("a@a"+s+".com");
        user.setUsername("A"+s);
        user.setPassword("123");
        user.setPasswordConfirmation("123");
        userService.Save(user);
        return user.getId().toString();
    }
}
