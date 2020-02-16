package com.presentation.demo.model.card;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;
import com.presentation.demo.model.card.product_offering.ProductOffering;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("debet")
public class DebetCard extends Card{

    private Boolean isOverdrafted;

    public DebetCard() {
    }

    public DebetCard(String cardNum, Bill bill, Date expireDate,
                     String cvv, Double maintenance, User cardHolder,
                     ProductOffering productOffering, Boolean isOverdrafted) {
        super(cardNum, bill, expireDate, cvv, maintenance, cardHolder, productOffering);
        this.isOverdrafted = isOverdrafted;
    }

    public Boolean isOverdrafted() {
        return isOverdrafted;
    }

    public void makeOverdrafted() {
        this.isOverdrafted = true;
    }

}
