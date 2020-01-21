package com.presentation.demo.service.transaction;

import com.presentation.demo.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction findTransactionById(Integer id);
    void delete(Transaction transaction);
    void save(Transaction transaction);
    List<Transaction> findAll();
}
