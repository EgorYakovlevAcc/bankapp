package com.presentation.demo.controller;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.card.Card;
import com.presentation.demo.model.card.DebetCard;
import com.presentation.demo.model.card.product_offering.ProductOffering;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import com.presentation.demo.service.card.product_offerings.ProductOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Random;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private BillService billService;

    @Autowired
    private ProductOfferingService productOfferingService;


//    @GetMapping("/createcard/{id}")
//    @ResponseBody
//    public String createCard(@PathVariable("id") Integer id) {
//        Random random = new Random();
//        Card card = new DebetCard();
//        Bill bill = billService.findBillById(id);
//        card.setBill(bill);
//        card.setCardHolder(bill.getHolder());
//        card.setCardNum(String.valueOf(Math.abs(random.nextInt())));
//        card.setCvv(String.valueOf(Math.abs(random.nextInt())));
//
//
//        card.setProductOffering(new ProductOffering(10000.0,50.0,7.5));
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 4);
//        card.setExpireDate(calendar.getTime());
//        cardService.save(card);
//        return card.getId().toString();
//    }

    @GetMapping("/createcard/{id}")
    public String createCard(@PathVariable("id") Integer id) {
        Random random = new Random();
        Card card = new DebetCard();
        Bill bill = billService.findBillById(id);
        card.setBill(bill);
        card.setCardHolder(bill.getHolder());
        card.setCardNum(String.valueOf(Math.abs(random.nextInt())));
        card.setCvv(String.valueOf(Math.abs(random.nextInt())));
        card.setProductOffering(productOfferingService.findProductOfferingsById(product_offering_id));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 4);
        card.setExpireDate(calendar.getTime());
        card.setBalance(0);
        cardService.save(card);
        return "redirect:/billdetails/" + id;
    }

    @GetMapping("/deletecard/{id}")
    public String deleteCard(@PathVariable("id") Integer id) {
        Card card = cardService.findCardById(id);
        Integer billId = card.getBill().getId();
        cardService.delete(card);
        return "redirect:/billdetails/" + billId;
    }
}
