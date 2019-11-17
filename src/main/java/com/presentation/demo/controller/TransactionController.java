package com.presentation.demo.controller;

import com.presentation.demo.model.Transaction;
import com.presentation.demo.model.TransactionType;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;

//todo: bind transaction to card, not only to bill

@Controller
public class TransactionController {

    @Autowired
    private BillService billService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/createtransaction/put")
    @ResponseBody
    public String createGetPutTransaction(@RequestParam("type") String type,
                                    @RequestParam("id") Integer id,
                                    @RequestParam("amount") Integer amount) {
        Transaction transaction;
        if (type.toUpperCase().equals("PUT_CASH"))
            transaction = new Transaction(Calendar.getInstance(), TransactionType.PUT_CASH, false, false,
                    billService.findBillById(id), billService.findBillById(id), amount);

        else
            transaction = new Transaction(Calendar.getInstance(), TransactionType.GET_CASH, false, false,
                    billService.findBillById(id), billService.findBillById(id), amount);
        transactionService.save(transaction);
        return "Transaction " + transaction.getId().toString() + " created";
    }

    @GetMapping("/createtransaction/transfer")
    @ResponseBody
    public String createTransferTransaction(@RequestParam("id1") Integer id1,
                                            @RequestParam("id2") Integer id2,
                                            @RequestParam("amount") Integer amount) {
        Transaction transaction = new Transaction(Calendar.getInstance(), TransactionType.TRANSFER, false, false,
                billService.findBillById(id1), billService.findBillById(id2), amount);
        transactionService.save(transaction);
        return "Transaction " + transaction.getId().toString() + " created";
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
