package com.presentation.demo.model.card;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;
import com.presentation.demo.model.card.product_offering.ProductOffering;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_type",discriminatorType = DiscriminatorType.STRING)
@Table(name = "cards")
public abstract class Card {

    @Id
//    @Column(name = "card_id") MUST BE DELETED!!!!!!!!!!!!
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cardNum;

    @ManyToOne(fetch =  FetchType.EAGER,cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    private Date expireDate;

    private BigDecimal balance;

    private String cvv;

    private Double maintenance;

    @ManyToOne(fetch = FetchType.EAGER )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User cardHolder;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_offering_id")
    private ProductOffering productOffering;

    public Card() {
    }

    public Card(String cardNum, Bill bill, Date expirDate, String cvv, Double maintenance, User cardHolder, ProductOffering productOffering) {
        this.cardNum = cardNum;
        this.bill = bill;
        this.expireDate = expirDate;
        this.cvv = cvv;
        this.maintenance = maintenance;
        this.cardHolder = cardHolder;
        this.productOffering = productOffering;
    }

    public Integer getId() {
        return id;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Double getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Double maintenance) {
        this.maintenance = maintenance;
    }

    public User getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(User cardHolder) {
        this.cardHolder = cardHolder;
    }

    public ProductOffering getProductOffering() {
        return productOffering;
    }

    public void setProductOffering(ProductOffering productOffering) {
        this.productOffering = productOffering;
    }

    public String getHiddenNum(){ return cardNum.substring(0, 4) + "****" + cardNum.substring(cardNum.length() - 4, cardNum.length()); }

}
