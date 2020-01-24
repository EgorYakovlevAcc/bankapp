package com.presentation.demo.controller;

import com.presentation.demo.errors.persistance.ConstraintViolationException;
import com.presentation.demo.helpers.MapEntryImpl;
import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static com.presentation.demo.constants.enums.AUTHORITIES.USER;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = {"/index", "/"})
    public String getIndex(Model model, @AuthenticationPrincipal User user) {
        boolean isAuthenticated = Objects.nonNull(user);
        String userName =  isAuthenticated ? user.getUsername() : null;
        model.addAttribute("userName", userName);


        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        if (isAuthenticated){
            namesLinksList.add(new MapEntryImpl<String,String>("Userpage","/userpage"));
        }
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
        newUser.setMobilePhoneNumber(new MobilePhoneNumber());
        model.addAttribute("user", newUser);
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@Valid @ModelAttribute("user") User user,Model model) throws Exception {

        MobilePhoneNumber userMobilePhoneNumber = user.getMobilePhoneNumber();
        user.setAuthority(USER);

        String newUserName = user.getUsername();

        String newUserPassword = user.getPassword();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        try{
            userService.save(user);
            mobilePhoneNumberService.save(userMobilePhoneNumber);
        }
        catch (PersistenceException exc){
            LOGGER.info("Invalid Mobile Phone Number = {}", exc.toString());
            model.addAttribute("validationMessage",exc.getMessage());
            return "registration";
        }

        securityService.autoLogin(newUserName,newUserPassword,authorities);
        return "redirect:/userpage";
    }

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
        return "redirect:/index";
    }

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "Delete success!";
    }

}
