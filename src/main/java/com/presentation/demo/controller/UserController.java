package com.presentation.demo.controller;

import com.presentation.demo.model.User;
import com.presentation.demo.repository.AuthenticatedUserRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

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
        userService.save(user);
        return user.getId().toString();
    }

    @GetMapping("/allusers")
    public String getAllUsers(Model model){
        model.addAttribute("users",userService.findAll());
        return "allusers";
    }

    @GetMapping("/userpage")
    public String getUserPage(@AuthenticationPrincipal User user, Model model){
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getUserPage: user = {}", user);
        }
        model.addAttribute("user",user);
        model.addAttribute("bills",user.getBills());
        model.addAttribute("cards",user.getCards());
        Integer id = user.getId();
        return "id";
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
