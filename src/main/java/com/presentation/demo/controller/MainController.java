package com.presentation.demo.controller;

import com.presentation.demo.helpers.MapEntryImpl;
import com.presentation.demo.helpers.UserWithMobilePhoneNumber;
import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.presentation.demo.constants.enums.AUTHORITIES.ADMIN;
import static com.presentation.demo.constants.enums.AUTHORITIES.USER;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Logger LOGGER = LoggerFactory.getLogger(MainController.class);


    @GetMapping(value = {"/index", "/"})
    public String getIndex(Model model, @AuthenticationPrincipal User user) {
        String userName =  Objects.nonNull(user) ? user.getUsername() : null;
        model.addAttribute("userName", userName);


        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();

        namesLinksList.add(new MapEntryImpl<String,String>("Home","/index"));
        namesLinksList.add(new MapEntryImpl<String,String>("Sign up","/registration"));
        namesLinksList.add(new MapEntryImpl<String,String>("Sign in","/login"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));

        model.addAttribute("namesLinksList",namesLinksList);
        return "index";
    }

    @GetMapping("/error")
    @ResponseBody
    public String getError() {
        return "error";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }


    @GetMapping("/registration")
    public String getRegistration(Model model) {
        User newUser = new User();
        MobilePhoneNumber phoneNumber = new MobilePhoneNumber();
        UserWithMobilePhoneNumber abstractUser = new UserWithMobilePhoneNumber(newUser,phoneNumber);
        model.addAttribute("abstractUser", abstractUser);
        return "registration";
    }

    @Transactional
    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("abstractUser") UserWithMobilePhoneNumber abstractUser) throws Exception {
        User newUser = abstractUser.getUser();
        MobilePhoneNumber newMobilePhoneNumber = abstractUser.getMobilePhoneNumber();

        newUser.setAuthority(USER);
        newMobilePhoneNumber.setOwner(newUser);

        String newUserName = newUser.getUsername();
        String newUserPassword = newUser.getPassword();
        Collection<? extends GrantedAuthority> authorities = newUser.getAuthorities();

        userService.save(newUser);
        mobilePhoneNumberService.save(newMobilePhoneNumber);


        LOGGER.info(newUserName);
        LOGGER.info(newUserPassword);

        Integer id = mobilePhoneNumberService.findMobilePhoneNumberByOwner(newUser).getId();

        securityService.autoLogin(newUserName,newUserPassword,authorities);
        return "redirect:/index";
    }

    //give data to server through forms
    @GetMapping("/login")
    public String getLogin(Model model, @AuthenticationPrincipal User authenticatedUser) {
        String authenticatedName = Objects.nonNull(authenticatedUser) ? authenticatedUser.getUsername() : null;
        model.addAttribute("authenticatedName",authenticatedName);
        model.addAttribute("user", new User());
        return "login";
    }

    @Transactional
    @PostMapping("/login")
    public String setLogin(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return "redirect:/userpage";
    }

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "Delete success!";
    }

}
