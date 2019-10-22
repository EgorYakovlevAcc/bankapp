package com.presentation.demo.controller;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.Card;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/createcard/{id}")
    @ResponseBody
    public String createCard(@PathVariable("id") Integer id) {
        Random random = new Random();
        Card card = new Card();
        Bill bill = billService.findBillById(id);
        card.setBill(bill);
        card.setCardHolder(bill.getHolder());
        card.setCardNum(String.valueOf(Math.abs(random.nextInt())));
        card.setCvv(String.valueOf(Math.abs(random.nextInt())));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 4);
        card.setExpirDate(calendar.getTime());
        cardService.save(card);
        return card.getId().toString();
    }

    @GetMapping("/deletecard/{id}")
    @ResponseBody
    public String deleteCard(@PathVariable("id") Integer id) {
        Card card = cardService.findCardById(id);
        cardService.delete(card);
        return "Card deleted";
    }
}
