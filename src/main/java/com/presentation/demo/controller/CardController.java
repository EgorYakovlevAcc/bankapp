package com.presentation.demo.controller;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.card.Card;
import com.presentation.demo.model.card.DebetCard;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import com.presentation.demo.service.card.product_offerings.ProductOfferingService;
import com.presentation.demo.service.generators.RangeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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

    @GetMapping("/createcard")
    public String createCard(@RequestParam Integer id,
                             @RequestParam Integer product_offering_id) {

        Random random = new Random();
        Card card = new DebetCard();
        Bill bill = billService.findBillById(id);
        card.setBill(bill);
        card.setCardHolder(bill.getHolder());
        card.setCardNum("10100101" +
                RangeGenerator.nextInt(1000, 9999) +
                RangeGenerator.nextInt(1000, 9999));
        card.setCvv(RangeGenerator.nextInt(100, 999));
        card.setProductOffering(productOfferingService.findProductOfferingsById(product_offering_id));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 4);
        card.setExpireDate(calendar.getTime());
        card.setBalance(BigDecimal.valueOf(0.0));
        cardService.save(card);
        return "redirect:/billdetails/" + id;
    }

    @GetMapping("/deletecard/{id}")
    public String deleteCard(@PathVariable("id") Integer id) {
        Card card = cardService.findCardById(id);
        Bill bill = card.getBill();
        Integer billId = card.getBill().getId();
        if (card.getBalance().compareTo(BigDecimal.valueOf(0.0)) == 1)
            return "redirect:/billdetails/" + billId;
        cardService.delete(card);
        return "redirect:/billdetails/" + billId;
    }
}
