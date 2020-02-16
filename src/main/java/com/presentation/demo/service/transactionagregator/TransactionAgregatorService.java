package com.presentation.demo.service.transactionagregator;

import com.presentation.demo.model.transaction.Transaction;

public interface TransactionAgregatorService {
    Transaction findTransactionById(Integer id);
    void save(Transaction transaction);
}
