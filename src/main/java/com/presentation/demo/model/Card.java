package com.presentation.demo.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String cardNum;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "bill_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bill bill;

    private Date expirDate;

    private Integer balance;

    private String cvv;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = true)
    private User cardHolder;

    public Card() {
    }

    public Card(String cardNum, Bill bill, Date expirDate, String cvv, User cardHolder) {
        this.cardNum = cardNum;
        this.bill = bill;
        this.expirDate = expirDate;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
    }

    public User getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(User cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Date getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(Date expirDate) {
        this.expirDate = expirDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
