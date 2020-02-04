package com.presentation.demo.model;

import com.presentation.demo.model.card.Card;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @Column(name = "bill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;

    private BigDecimal balance = BigDecimal.ZERO;

    private Date due;

    private Date lastTransaction;

    @OneToMany(mappedBy = "targetBill", cascade = CascadeType.ALL)
    private List<DateBalanceHistory> balanceHistory;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Card> cards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
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

    public Bill(String number, BigDecimal balance, Date due, Date lastTransaction, User holder) {
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

    public BigDecimal getBalance() {
        BigDecimal result = BigDecimal.valueOf(0);
        for (Card card : this.getCards()) {
            result = result.add(card.getBalance());
        }
        return result;
    }

    public void setBalance(BigDecimal balance) {
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
