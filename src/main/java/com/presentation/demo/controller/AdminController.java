package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.pojo.MapEntryImpl;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    private BillService billService;

    @Autowired
    private CardService cardService;

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

//    @GetMapping("")

}
