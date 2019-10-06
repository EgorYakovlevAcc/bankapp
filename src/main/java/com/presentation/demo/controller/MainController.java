package com.presentation.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping(value = {"/", "/index"})
    public String getIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("username", "");
        model.addAttribute("password", "");
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @GetMapping("/userpage")
    public String getUserpage() {
        return "userpage";
    }

    @PostMapping("/login")
    //@RequestParam(required = false, defaultValue = "someValue", value="someAttr") String someAttr
    //http://something.com/1?someAttr=someValue
    public String printLogin(Model model, @RequestParam String username, @RequestParam String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        return getLogin(model);
    }
}
