package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/createuser")
    @ResponseBody
    public String createUser(){
        User user = new User();
        Random rand = new Random();
        user.setEmail("a@a"+ Math.abs(rand.nextInt()) +".com");
        user.setUsername("A" + Math.abs(rand.nextInt()));
        user.setPassword("123");
        user.setPasswordConfirmation("123");
        userService.save(user);
        return user.getId().toString();
    }

    @GetMapping("/allusers")
    public String getAllUsers(Model model){
        model.addAttribute("users",userService.findAll());
        return "index";
//        return userService.findAll().stream()
//                .map(user -> user.getUsername())
//                .collect(Collectors.toList());
    }

    @GetMapping("/userpage")
    public String getUserPage(@RequestParam("userid") Integer userId,Model model){
        User user = userService.findUserById(userId);
        model.addAttribute("userId",user.getId());
        model.addAttribute("userName",user.getUsername());
        model.addAttribute("bills",user.getBills());
        return "userpage";
    }
}
