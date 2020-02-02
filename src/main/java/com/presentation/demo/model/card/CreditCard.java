package com.presentation.demo.model.card;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;
import com.presentation.demo.model.card.product_offering.ProductOffering;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("credit")
public class CreditCard extends Card {

    private Date paymentDate;

    private Boolean isEarlyPaymentAvailable;

    public CreditCard() {
    }

    public CreditCard(String cardNum, Bill bill, Date expireDate, String cvv, Double maintenance, User cardHolder, ProductOffering productOffering, Date paymentDate, Boolean isEarlyPaymentAvailable) {
        super(cardNum, bill, expireDate, cvv, maintenance, cardHolder, productOffering);
        this.paymentDate = paymentDate;
        this.isEarlyPaymentAvailable = isEarlyPaymentAvailable;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean isEarlyPaymentAvailable() {
        return isEarlyPaymentAvailable;
    }

    public void makeEarlyPaymentAvailable() {
        this.isEarlyPaymentAvailable = true;
    }

}
