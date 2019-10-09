package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.repository.UserRepository;
import com.presentation.demo.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private UserSevice userSevice;

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
        return "redirect:userpage";
    }

    @PostMapping("/registration")
    public String printRegistration(Model model, @RequestParam String username, @RequestParam String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        return "redirect:userpage";
    }

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userSevice.findUserById(id);
        userSevice.delete(user);
        return "delete success";
    }
}
