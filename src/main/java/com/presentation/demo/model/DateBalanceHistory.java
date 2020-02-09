package com.presentation.demo.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "date_balance_history")
public class DateBalanceHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bill targetBill;

    private BigDecimal balance;

    private Date date;

    public DateBalanceHistory(){

    }

    public DateBalanceHistory(Bill targetBill, BigDecimal balance, Date date) {
        this.targetBill = targetBill;
        this.balance = balance;
        this.date = date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
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
