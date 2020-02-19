package com.presentation.demo.controller.admin;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;
import com.presentation.demo.model.card.Card;
import com.presentation.demo.pojo.MapEntryImpl;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

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

    @Autowired
    private UserValidator userValidator;

    @GetMapping("")
    public String getAdminPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        namesLinksList.add(new MapEntryImpl<String,String>("Index","/index"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
        Map<Date,Integer> mapa = new HashMap<>();
        mapa.put(new Date(),123);
        model.addAttribute("map",mapa);
        model.addAttribute("namesLinksList",namesLinksList);

        return "admin/admin";
    }


    @GetMapping("/bills")
    public String getAdminBills(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
        namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
        model.addAttribute("namesLinksList",namesLinksList);

        List<Bill> bills = billService.findAll();

        model.addAttribute("bills",bills);

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

        List<Card> cards = cardService.findAll();

        model.addAttribute("cards",cards);

        return "admin/adminCards";
    }



}
