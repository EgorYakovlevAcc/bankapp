package com.presentation.demo.repository;

import com.presentation.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findTransactionById(Integer Id);
    List<Transaction> findTransactionByDate(Calendar date);
}
