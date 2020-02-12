package com.presentation.demo.controller;

import com.presentation.demo.constants.Params;
import com.presentation.demo.model.Bill;
import com.presentation.demo.model.transaction.Transaction;
import com.presentation.demo.model.transaction.TransactionType;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.transactionagregator.TransactionAgregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Calendar;

@Transactional
@Controller
public class TransactionAgregatorController {

    @Autowired
    private BillService billService;

    @Autowired
    private TransactionAgregatorService transactionAgregatorService;

    @GetMapping("/execute")
    @ResponseBody
    public String executeTransaction(@RequestParam("id") Integer id) {
        Transaction transaction = transactionAgregatorService.findTransactionById(id);
        long diff = transaction.getDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        if (diff >= Params.TEN_DAYS_SEC) {
            transaction.setCanceled(true);
            transactionAgregatorService.save(transaction);
            return "Time for transaction is over";
        }
        if (transaction.getTransactionType() == TransactionType.TRANSFER) {
            Bill sender = transaction.getSender();
            Bill recipient = transaction.getRecipient();
            sender.setBalance(sender.getBalance().subtract(BigDecimal.valueOf(transaction.getSum())));
            recipient.setBalance(recipient.getBalance().add(BigDecimal.valueOf(transaction.getSum())));
            billService.save(sender);
            billService.save(recipient);
            transaction.setCompleted(true);
            transactionAgregatorService.save(transaction);
        }
        else if (transaction.getTransactionType() == TransactionType.GET_CASH) {
            Bill sender = transaction.getSender();
            sender.setBalance(sender.getBalance().subtract(BigDecimal.valueOf(transaction.getSum())));
            billService.save(sender);
            transaction.setCompleted(true);
            transactionAgregatorService.save(transaction);
        }
        else {
            Bill sender = transaction.getSender();
            sender.setBalance(sender.getBalance().add(BigDecimal.valueOf(transaction.getSum())));
            billService.save(sender);
            transaction.setCompleted(true);
            transactionAgregatorService.save(transaction);
        }
        return "Transaction successful complete";
    }
}
