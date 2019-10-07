package com.presentation.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @GetMapping(value = {"/", "/index"})
    public String getIndex() {
       return "index";
   }

   @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("username", "");
        model.addAttribute("password", "");
        return "login";
   }

   @GetMapping("/registration")
    public String getRegistration(){
        return ("registration");
   }

   @GetMapping("/userpage")
    public String getUserpage() {
        return ("userpage");
   }

   @GetMapping("/login/{username}/{password}")
    public String printLogin(@PathVariable String username, @PathVariable String password){
       System.out.println("username: " + username);
       System.out.println("password: " + password);
        return "redirect:/index";
    }
}
