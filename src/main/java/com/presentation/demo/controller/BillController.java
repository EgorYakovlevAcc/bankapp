package com.presentation.demo.controller;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.Card;
import com.presentation.demo.model.DateBalanceHistory;
import com.presentation.demo.model.User;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import com.presentation.demo.service.datebalancehistory.DateBalanceHistoryService;
import com.presentation.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.*;

@Controller
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private DateBalanceHistoryService dateBalanceHistoryService;

    @GetMapping("/createbill/{id}")
    @ResponseBody
    public String createBill(@PathVariable("id") Integer id) {

        Random rand = new Random();
        Bill bill = new Bill();
        User user = userService.findUserById(id);
        bill.setHolder(user);
//        bill.setBalance(new BigInteger(String.valueOf(Math.abs(rand.nextInt()))));
        bill.setNumber(String.valueOf(Math.abs(rand.nextInt())));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 4);
        bill.setDue(calendar.getTime());
        billService.save(bill);
        return bill.getId().toString();
    }

    @GetMapping("/deletebill/{id}")
    @ResponseBody
    public String deleteBill(@PathVariable("id") Integer id){
        Bill bill = billService.findBillById(id);
        billService.delete(bill);
        return "Delete success!";
    }

    @GetMapping("/bill")
    public String getBill(@RequestParam("billid") Integer billId, Model model){
        Bill bill = billService.findBillById(billId);
        model.addAttribute("billId",bill.getId());
        model.addAttribute("holderId",bill.getHolder().getId());
        Map<Date, BigInteger> dateBigIntegerMap = new TreeMap<>();
        List<DateBalanceHistory> dateBalanceHistories = dateBalanceHistoryService.findDateBalanceHistoriesByBill(bill);
        for (DateBalanceHistory dateBalanceHistory: dateBalanceHistories) {
            dateBigIntegerMap.put(dateBalanceHistory.getDate(), dateBalanceHistory.getBalance());
        }
        model.addAttribute("map", dateBigIntegerMap);
        return "bill";
    }

    @GetMapping("/cards")
    public String getCards(@RequestParam("billid") Integer billid, Model model) {
        Bill bill = billService.findBillById(billid);
        model.addAttribute("billid", bill.getId());
        model.addAttribute("holderid", bill.getHolder().getId());
        List<Card> cards = cardService.findCardsByBill(bill);
        Map<String, Integer> cardsMap = new TreeMap<>();
        for (Card card : cards) {
            cardsMap.put(card.getCardNum(), card.getBalance());
        }
        model.addAttribute("map", cardsMap);
        return "cards";
    }

}
