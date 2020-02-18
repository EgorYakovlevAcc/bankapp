package com.presentation.demo.controller;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.pojo.MapEntryImpl;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import com.presentation.demo.service.user.UserService;
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

import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_ADMIN;
import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_USER;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private Logger ADMIN_LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Autowired
    private BillService billService;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("")
    public String getAdminPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        namesLinksList.add(new MapEntryImpl<String,String>("Manage Users","/admin/users"));
        namesLinksList.add(new MapEntryImpl<String, String>("Manage Cards","/admin/cards"));
        namesLinksList.add(new MapEntryImpl<String, String>("Manage Bills","/admin/bills"));
        namesLinksList.add(new MapEntryImpl<String, String>("Product Offerings","/admin/offers"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
        model.addAttribute("namesLinksList",namesLinksList);

        return "admin/admin";
    }

    @GetMapping("/users")
    public String getAdminUsers(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
        namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
        model.addAttribute("namesLinksList",namesLinksList);

        List<User> nonAdminUsers = userService.findUsersByRoleEquals(ROLE_USER.getAuthority());
        List<User> adminUsers = userService.findUsersByRoleEquals(ROLE_ADMIN.getAuthority());

        model.addAttribute("admins",adminUsers);
        model.addAttribute("nonAdmins",nonAdminUsers);

        return "admin/adminUsers";
    }

    @GetMapping("/bills")
    public String getAdminBills(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
        namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
        model.addAttribute("namesLinksList",namesLinksList);

        return "admin/adminBills";
    }

    @GetMapping("/cards")
    public String getAdminCards(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
        namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
        model.addAttribute("namesLinksList",namesLinksList);


        return "admin/adminCards";
    }

    @GetMapping("/createuser/")
    public String getCreateUser(Model model){
        User newUser = new User();
        newUser.setMobilePhoneNumber(new MobilePhoneNumber());
        model.addAttribute("user", newUser);
        return "createUserForm";//maybe simple registration
    }

    @PostMapping("/createuser")
    public String postRegistration(@Valid @ModelAttribute("user") User user, BindingResult resultUser,
                                   Model model){

        MobilePhoneNumber userMobilePhoneNumber = user.getMobilePhoneNumber();
        userValidator.validate(user,resultUser);

        if (resultUser.hasFieldErrors()){
            List<FieldError> validationErrors = resultUser.getFieldErrors();
            for(FieldError validationError: validationErrors){
                ADMIN_LOGGER.info("In field " + "\"" + validationError.getField() +  "\""  + " : " + validationError.getDefaultMessage());
                model.addAttribute(validationError.getField(),validationError.getDefaultMessage());
            }
            return "registration";
        }
        else if (resultUser.hasGlobalErrors()){
            ADMIN_LOGGER.info("Global errors!");
            return "registration";
        }
        else{
            userService.createUserWithActivationCode(user);
            mobilePhoneNumberService.save(userMobilePhoneNumber);
            return "redirect:/userpage";
        }

    }

    @GetMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(userService.findUserById(id));
        return "redirect:/admin/users";
    }

}
