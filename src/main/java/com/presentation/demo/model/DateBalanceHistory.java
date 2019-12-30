package com.presentation.demo.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "date_balance_history")
public class DateBalanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

    private Bill targetBill;

    private BigInteger balance;

    private Date date;

    public DateBalanceHistory(){

    }

    public DateBalanceHistory(Bill targetBill, BigInteger balance, Date date) {
        this.targetBill = targetBill;
        this.balance = balance;
        this.date = date;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Bill getTargetBill() {
        return targetBill;
    }

    public void setTargetBill(Bill targetBill) {
        this.targetBill = targetBill;
    }

    public Integer getId() {
        return id;
    }
}
