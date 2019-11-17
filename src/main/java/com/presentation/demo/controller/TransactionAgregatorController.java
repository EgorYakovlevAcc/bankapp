package com.presentation.demo.controller;

import com.presentation.demo.constants.Constant;
import com.presentation.demo.model.Bill;
import com.presentation.demo.model.DateBalanceHistory;
import com.presentation.demo.model.Transaction;
import com.presentation.demo.model.TransactionType;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.datebalancehistory.DateBalanceHistoryService;
import com.presentation.demo.service.transaction.TransactionService;
import com.presentation.demo.service.transactionagregator.TransactionAgregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

//todo: clear transaction list

@Transactional
@Controller
public class TransactionAgregatorController {

    @Autowired
    private BillService billService;

    @Autowired
    private TransactionAgregatorService transactionAgregatorService;

    @Autowired
    private DateBalanceHistoryService dateBalanceHistoryService;

    @GetMapping("/completetransaction")
    @ResponseBody
    public String executeTransaction(@RequestParam("id") Integer id) {
        Transaction transaction = transactionAgregatorService.findTransactionById(id);
        long diff = transaction.getDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        if (diff >= Constant.tenDays) {
            transaction.setCanceled(true);
            transactionAgregatorService.save(transaction);
            return "Time for transaction is over";
        }
        if (transaction.getTransactionType() == TransactionType.TRANSFER) {
            Bill sender = transaction.getSender();
            Bill recipient = transaction.getRecipient();

            sender.setBalance(sender.getBalance().subtract(BigInteger.valueOf(transaction.getSum())));
            recipient.setBalance(recipient.getBalance().add(BigInteger.valueOf(transaction.getSum())));

            sender.setLastTransaction(new Date());
            recipient.setLastTransaction(new Date());

            billService.save(sender);
            billService.save(recipient);

            dateBalanceHistoryService.save(new DateBalanceHistory(sender,sender.getBalance(),new Date()));

            dateBalanceHistoryService.save(new DateBalanceHistory(recipient,recipient.getBalance(),new Date()));

            transaction.setCompleted(true);
            transactionAgregatorService.save(transaction);
        }
        else if (transaction.getTransactionType() == TransactionType.GET_CASH) {
            Bill sender = transaction.getSender();
            sender.setBalance(sender.getBalance().subtract(BigInteger.valueOf(transaction.getSum())));
            sender.setLastTransaction(new Date());

            billService.save(sender);

            dateBalanceHistoryService.save(new DateBalanceHistory(sender,sender.getBalance(),new Date()));

            transaction.setCompleted(true);
            transactionAgregatorService.save(transaction);
        }
        else {
            Bill sender = transaction.getSender();
            sender.setBalance(sender.getBalance().add(BigInteger.valueOf(transaction.getSum())));
            sender.setLastTransaction(new Date());

            billService.save(sender);

            dateBalanceHistoryService.save(new DateBalanceHistory(sender,sender.getBalance(),new Date()));

            transaction.setCompleted(true);
            transactionAgregatorService.save(transaction);
        }


        return "Transaction successfully completed";
    }
}
