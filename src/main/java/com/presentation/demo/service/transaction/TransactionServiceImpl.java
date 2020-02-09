package com.presentation.demo.service.transaction;

import com.presentation.demo.model.transaction.Transaction;
import com.presentation.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl() {
    }

    @Override
    public Transaction findTransactionById(Integer id) {
        return transactionRepository.findTransactionById(id);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
