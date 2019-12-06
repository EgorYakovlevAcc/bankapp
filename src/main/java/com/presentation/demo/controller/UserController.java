package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.repository.UserRepository;
import com.presentation.demo.service.datebalancehistory.DateBalanceHistoryService;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Random;

import static com.presentation.demo.constants.enums.ROLES.USER;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DateBalanceHistoryService dateBalanceHistoryService;

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/createuser")
    @ResponseBody
    public String createUser(){
        User user = new User();
        Random rand = new Random();
        user.setEmail("a@a"+ Math.abs(rand.nextInt()) +".com");
        user.setUsername("A" + Math.abs(rand.nextInt()));
        user.setPassword("123");
        user.setPasswordConfirmation("123");
        user.setRole(USER);
        userService.save(user);
        return user.getId().toString();
    }

    @GetMapping("/allusers")
    public String getAllUsers(Model model){
        model.addAttribute("users",userService.findAll());
        return "allusers";
    }

    @GetMapping(value = {"/userpage"})
    public String getUserPage(@AuthenticationPrincipal User user, Model model){
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getUserPage: user = {}", user);
        }
        //todo: if admin -> admin, if userpage -> userpage
        Long id = user.getId();
        model.addAttribute("bills",user.getBills());
        model.addAttribute("cards",user.getCards());
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("redirect:/userpage/" + id);
        return "redirect:/userpage/" + id;
    }


//    @GetMapping("/userpage")
//    public String getUserPage(@RequestParam("userid") Integer userId,Model model){
//        User user = userService.findUserById(userId);
//        List<Bill> bills = user.getBills();
//        List<Map<Date,BigInteger>> billsDateBigIntegerMaps = new LinkedList<Map<Date,BigInteger>>();
//        for(Bill bill:bills){
//            Map<Date,BigInteger> billDateBigIntegerMap = new TreeMap<>();
//            List<DateBalanceHistory> dateBalanceHistories = dateBalanceHistoryService.findDateBalanceHistoriesByBill(bill);
//            for (DateBalanceHistory dateBalanceHistory: dateBalanceHistories) {
//                billDateBigIntegerMap.put(dateBalanceHistory.getDate(), dateBalanceHistory.getBalance());
//            }
//            billsDateBigIntegerMaps.add(billDateBigIntegerMap);
//        }
//        model.addAttribute("userId",user.getId());
//        model.addAttribute("user",user);
//        model.addAttribute("bills",user.getBills());
//        model.addAttribute("billsMaps",billsDateBigIntegerMaps);
//        return "userpage";
//    }
}
