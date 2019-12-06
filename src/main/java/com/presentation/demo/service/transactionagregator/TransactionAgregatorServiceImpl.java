package com.presentation.demo.service.transactionagregator;

import com.presentation.demo.model.Transaction;
import com.presentation.demo.repository.TransactionRepository;
import com.presentation.demo.service.bill.BillService;
import com.presentation.demo.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionAgregatorServiceImpl implements TransactionAgregatorService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction findTransactionById(Integer id) {
        return transactionRepository.findTransactionById(id);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}