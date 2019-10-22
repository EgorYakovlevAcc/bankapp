package com.presentation.demo.controller;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.DateBalanceHistory;
import com.presentation.demo.model.User;
import com.presentation.demo.service.bill.BillService;
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
    private DateBalanceHistoryService dateBalanceHistoryService;

    @GetMapping("/createbill/{id}")
    @ResponseBody
    public String createBill(@PathVariable("id") Integer id){

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

    @GetMapping("/add")
    @ResponseBody
    public String increaseBillBalance(@RequestParam("billid") Integer billId, @RequestParam("amount") BigInteger amount){
        Bill bill = billService.findBillById(billId);
        bill.setBalance(bill.getBalance().add(amount));
        dateBalanceHistoryService.createNewDateBalanceHistory(bill);
        int size = dateBalanceHistoryService.findDateBalanceHistoriesByBill(bill).size();
        bill.setLastTransaction(dateBalanceHistoryService.findDateBalanceHistoriesByBill(bill).get(size - 1).getDate());
        billService.save(bill);
        return "Increase success!";
    }

    @GetMapping("/dec")
    @ResponseBody
    public String decreaseBillBalance(@RequestParam("billid") Integer billId, @RequestParam("amount") BigInteger amount){
        Bill bill = billService.findBillById(billId);
        bill.setBalance(bill.getBalance().subtract(amount));
        dateBalanceHistoryService.createNewDateBalanceHistory(bill);
        int size = dateBalanceHistoryService.findDateBalanceHistoriesByBill(bill).size();
        bill.setLastTransaction(dateBalanceHistoryService.findDateBalanceHistoriesByBill(bill).get(size - 1).getDate());
        billService.save(bill);
        return "Decreased success!";
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


}