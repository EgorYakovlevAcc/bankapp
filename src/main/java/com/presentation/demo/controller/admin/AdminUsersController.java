package com.presentation.demo.controller.admin;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.pojo.MapEntryImpl;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
import com.presentation.demo.service.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.presentation.demo.constants.Properties.EMAIL_PATTERN;
import static com.presentation.demo.constants.Properties.PHONE_NUMBER_PATTERN;
import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_ADMIN;
import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_USER;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {

    private static Logger ADMIN_USERS_LOGGER = LoggerFactory.getLogger(AdminUsersController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("")
    public String getAdminUsers(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
        namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
        model.addAttribute("namesLinksList",namesLinksList);

        List<User> usersOrdered = userService.findUsersByRoleEquals(ROLE_ADMIN.getAuthority());
        usersOrdered.addAll(userService.findUsersByRoleEquals(ROLE_USER.getAuthority()));

        model.addAttribute("users",usersOrdered);

        return "/admin/adminUsers";
    }

    @GetMapping("/createuser")
    public String getCreateUser(Model model){
        User newUser = new User();
        newUser.setMobilePhoneNumber(new MobilePhoneNumber());
        model.addAttribute("user", newUser);
        return "/admin/createuserForm";
    }

    @PostMapping("/createuser")
    public String postCreateUser(@Valid @ModelAttribute("user") User user, BindingResult resultUser,
                                 Model model){

        MobilePhoneNumber userMobilePhoneNumber = user.getMobilePhoneNumber();
        userValidator.validate(user,resultUser);

        if (resultUser.hasFieldErrors()){
            List<FieldError> validationErrors = resultUser.getFieldErrors();
            for(FieldError validationError: validationErrors){
                ADMIN_USERS_LOGGER.info("In field " + "\"" + validationError.getField() +  "\""  + " : " + validationError.getDefaultMessage());
                model.addAttribute(validationError.getField(),validationError.getDefaultMessage());
            }
            return "/admin/createuserForm";
        }
        else if (resultUser.hasGlobalErrors()){
            ADMIN_USERS_LOGGER.info("Global errors!");
            return "/admin/createuserForm";
        }
        else{
            String rawPassword = user.getPassword();
            userService.createUserWithActivationCode(user);
            mobilePhoneNumberService.save(userMobilePhoneNumber);
            return "redirect:/admin/users";
        }

    }

    @GetMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(userService.findUserById(id));
        return "redirect:/admin/users";
    }


    @GetMapping("/change/username/{id}")
    public String changeUsername(@PathVariable("id") Integer userId,@RequestParam(value = "username") String newUsername,Model model,@AuthenticationPrincipal User user){
        User target = userService.findUserById(userId);
        if (userService.findUserByUsername(newUsername) == null){
            target.setUsername(newUsername);
            userService.save(target);
            return "redirect:/admin/users";
        }
        else{
            if (target.getUsername().equals(newUsername)){
                model.addAttribute("message","Please change username to a different one!");
            }
            else{
                model.addAttribute("message","User with this username already exists!");
            }

            model.addAttribute("user", user);

            List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
            namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
            namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
            model.addAttribute("namesLinksList",namesLinksList);

            List<User> usersOrdered = userService.findUsersByRoleEquals(ROLE_ADMIN.getAuthority());
            usersOrdered.addAll(userService.findUsersByRoleEquals(ROLE_USER.getAuthority()));

            model.addAttribute("users",usersOrdered);

            return "/admin/adminUsers";
        }
    }

    @GetMapping("/change/email/{id}")
    public String changeEmail(@PathVariable("id") Integer userId,@RequestParam(value = "email") String newEmail, Model model, @AuthenticationPrincipal User user){

        User target = userService.findUserById(userId);
        User existingUser = userService.findUserByEmail(newEmail);
        boolean matches = Pattern.matches(EMAIL_PATTERN,newEmail);

        if (matches && (existingUser == null)){
            target.setEmail(newEmail);
            userService.save(target);
            return "redirect:/admin/users/";
        }
        else {
            if (!matches){
                model.addAttribute("message","Wrong email format!");
            }
            else{
                model.addAttribute("message","User with this email already exists!");
            }
            model.addAttribute("user", user);

            List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
            namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
            namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
            model.addAttribute("namesLinksList",namesLinksList);

            List<User> usersOrdered = userService.findUsersByRoleEquals(ROLE_ADMIN.getAuthority());
            usersOrdered.addAll(userService.findUsersByRoleEquals(ROLE_USER.getAuthority()));

            model.addAttribute("users",usersOrdered);
            return "/admin/adminUsers";
        }
    }

    @GetMapping("/change/phone/{id}")
    public String changePhoneNumber(@PathVariable("id") Integer userId,@RequestParam(value = "phone") String newPhoneNumberValue, Model model, @AuthenticationPrincipal User user){

        User target = userService.findUserById(userId);
        MobilePhoneNumber targetMobilePhoneNumber = mobilePhoneNumberService.findMobilePhoneNumberByMobilePhoneNumberValue(newPhoneNumberValue);

        boolean matches = Pattern.matches(PHONE_NUMBER_PATTERN,newPhoneNumberValue);

        if (matches && (targetMobilePhoneNumber == null)){
            MobilePhoneNumber userPhoneNumber = target.getMobilePhoneNumber();
            userPhoneNumber.setMobilePhoneNumberValue(newPhoneNumberValue);
            mobilePhoneNumberService.save(userPhoneNumber);
            userService.save(target);
            return "redirect:/admin/users/";
        }
        else {
            if (!matches){
                model.addAttribute("message","Wrong phone number format!");
            }
            else{
                model.addAttribute("message","This phone number has already been used!");
            }
            model.addAttribute("user", user);

            List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
            namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
            namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
            model.addAttribute("namesLinksList",namesLinksList);

            List<User> usersOrdered = userService.findUsersByRoleEquals(ROLE_ADMIN.getAuthority());
            usersOrdered.addAll(userService.findUsersByRoleEquals(ROLE_USER.getAuthority()));

            model.addAttribute("users",usersOrdered);
            return "/admin/adminUsers";
        }
    }
}
