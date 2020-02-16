package com.presentation.demo.service.transactionagregator;

import com.presentation.demo.constants.Params;
import com.presentation.demo.model.card.Card;
import com.presentation.demo.model.transaction.Transaction;
import com.presentation.demo.model.transaction.TransactionType;
import com.presentation.demo.repository.TransactionRepository;
import com.presentation.demo.service.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

@Service
@Transactional
@EnableScheduling
public class TransactionAgregatorServiceImpl implements TransactionAgregatorService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private TransactionAgregatorService transactionAgregatorService;

    private Queue<Transaction> transactionQueue = new LinkedList<>();

    @Override
    public Transaction findTransactionById(Integer id) {
        return transactionRepository.findTransactionById(id);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void addTransactionToQueue(Transaction transaction) {
        transactionQueue.offer(transaction);
    }

    @Scheduled(fixedDelay = Params.TRANSACTION_EXECUTOR_DELAY)
    public void executeTransactions() {
        Transaction transaction;
        while (!transactionQueue.isEmpty()) {
            transaction = transactionQueue.poll();
            if (transaction.getTransactionType() == TransactionType.TRANSFER) {
                Card sender = transaction.getSender();
                Card recipient = transaction.getRecipient();
                sender.setBalance(sender.getBalance().subtract(BigDecimal.valueOf(transaction.getSum())));
                recipient.setBalance(recipient.getBalance().add(BigDecimal.valueOf(transaction.getSum())));
                cardService.save(sender);
                cardService.save(recipient);
                transaction.setCompleted(true);
                transactionAgregatorService.save(transaction);
            }
            else if (transaction.getTransactionType() == TransactionType.GET_CASH) {
                Card sender = transaction.getSender();
                sender.setBalance(sender.getBalance().subtract(BigDecimal.valueOf(transaction.getSum())));
                cardService.save(sender);
                transaction.setCompleted(true);
                transactionAgregatorService.save(transaction);
            }
            else {
                Card sender = transaction.getSender();
                sender.setBalance(sender.getBalance().add(BigDecimal.valueOf(transaction.getSum())));
                cardService.save(sender);
                transaction.setCompleted(true);
                transactionAgregatorService.save(transaction);
            }
        }
    }
}
