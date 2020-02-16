package com.presentation.demo.controller;

import com.presentation.demo.model.card.Card;
import com.presentation.demo.model.transaction.Transaction;
import com.presentation.demo.model.transaction.TransactionType;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.card.CardService;
import com.presentation.demo.service.transaction.TransactionService;
import com.presentation.demo.service.transactionagregator.TransactionAgregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Calendar;

//todo: bind transaction to card, not only to bill

@Controller
public class TransactionController {

    @Autowired
    private BillService billService;

    @Autowired
    private CardService cardService;

    @Autowired
    private TransactionAgregatorService transactionAgregatorService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/createtransaction/get-put")
    @ResponseBody
    public String createGetPutTransaction(@RequestParam("type") String type,
                                    @RequestParam("id") Integer id,
                                    @RequestParam("amount") Integer amount) {
        Transaction transaction;
        Card sender = cardService.findCardById(id);
        if (type.toUpperCase().equals("PUT_CASH"))
            transaction = new Transaction(Calendar.getInstance(), TransactionType.PUT_CASH, false, false,
                    sender, sender, amount);
        else {
            if (sender.getBalance().subtract(BigDecimal.valueOf(amount))
                    .compareTo(BigDecimal.valueOf(0)) == -1) { return "Unsuccessful"; }
                transaction = new Transaction(Calendar.getInstance(),
                        TransactionType.GET_CASH, false,
                        false, sender, sender, amount);
        }
        transactionService.save(transaction);
        transactionAgregatorService.addTransactionToQueue(transaction);
        return "Successful op";
    }

    @GetMapping("/createtransaction/transfer")
    public String createTransferTransaction(@RequestParam("id") Integer id,
                                            @RequestParam("cardNumber") String cardNumber,
                                            @RequestParam("amount") Integer amount,
                                            @RequestParam("bill") Integer bill) {
        Card sender = cardService.findCardById(id);
        Card recipient = cardService.findCardByCardNum(cardNumber);
        if (sender.getBalance().subtract(BigDecimal.valueOf(amount)).compareTo(BigDecimal.valueOf(0)) != -1) {
            Transaction transaction = new Transaction(Calendar.getInstance(), TransactionType.TRANSFER, false, false,
                    sender, recipient, amount);
            transactionService.save(transaction);
            transactionAgregatorService.addTransactionToQueue(transaction);
        }
        return "redirect:/billdetails/" + bill;
    }


    @GetMapping("/canceltransaction")
    @ResponseBody
    public String canceledTransaction(@RequestParam("id") Integer id) {
        Transaction transaction = transactionService.findTransactionById(id);
        transaction.setCanceled(true);
        transactionService.save(transaction);
        return "Transaction " + transaction.getId().toString() + " canceled";
    }
}
