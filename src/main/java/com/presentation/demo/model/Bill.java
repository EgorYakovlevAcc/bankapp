package com.presentation.demo.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String number;

    private BigInteger balance = BigInteger.ZERO;

    private Date due;

    private Date lastTransaction;

    @OneToMany(mappedBy = "targetBill", cascade = CascadeType.ALL)
    private List<DateBalanceHistory> balanceHistory;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Card> cards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User holder;


    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public Bill() {
    }

    public Bill(String number, BigInteger balance, Date due, Date lastTransaction,User holder) {
        this.number = number;
        this.balance = balance;
        this.due = due;
        this.lastTransaction = lastTransaction;
        this.holder = holder;
    }

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigInteger getBalance() {
        BigInteger result = BigInteger.valueOf(0);
        for (Card card : this.getCards()) {
            result = result.add(BigInteger.valueOf((long)card.getBalance()));
        }
        return result;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Date getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(Date lastTransaction) {
        this.lastTransaction = lastTransaction;
    }

    public List<DateBalanceHistory> getBalanceHistory() {
        return balanceHistory;
    }

    public void setBalanceHistory(List<DateBalanceHistory> balanceHistory) {
        this.balanceHistory = balanceHistory;
    }
}
