package com.presentation.demo.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean isCanceled;

    private boolean isCompleted;

    private Calendar date;

    private TransactionType transactionType;

    @OneToOne
    private Bill sender;

    @OneToOne
    private Bill recipient;

    private int sum;

    public Transaction(Calendar date, TransactionType transactionType, boolean isCanceled,
                       boolean isCompleted, Bill sender, Bill recipient, int sum) {
        this.isCanceled = isCanceled;
        this.isCompleted = isCompleted;
        this.sender = sender;
        this.recipient = recipient;
        this.sum = sum;
        this.transactionType = transactionType;
        this.date = date;
    }

    public Transaction() {
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getId() {
        return id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Bill getSender() {
        return sender;
    }

    public void setSender(Bill sender) {
        this.sender = sender;
    }

    public Bill getRecipient() {
        return recipient;
    }

    public void setRecipient(Bill recipient) {
        this.recipient = recipient;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
