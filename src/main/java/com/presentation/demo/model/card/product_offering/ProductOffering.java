package com.presentation.demo.model.card.product_offering;

import com.presentation.demo.model.card.Card;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_offerings")
public class ProductOffering {

    @Id
    @Column(name = "product_offering_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double limits;

    private Double percentage;

    @Column(name = "cash_percentage")
    private Double cashbackPercentage;

    @OneToMany(mappedBy = "productOffering", cascade = CascadeType.ALL)
    private List<Card> cards;


    public ProductOffering() {
    }

    public ProductOffering(Double limits, Double percentage, Double cashbackPercentage) {
        this.limits = limits;
        this.percentage = percentage;
        this.cashbackPercentage = cashbackPercentage;
    }

    public Integer getId() {
        return id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> card) {
        this.cards = card;
    }

    public Double getLimits() {
        return limits;
    }

    public void setLimits(Double limit) {
        this.limits = limit;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getCashbackPercentage() {
        return cashbackPercentage;
    }

    public void setCashbackPercentage(Double cashbackPercentage) {
        this.cashbackPercentage = cashbackPercentage;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        if (another == null || getClass() != another.getClass()) return false;
        ProductOffering that = (ProductOffering) another;
        return Objects.equals(limits, that.limits) &&
                Objects.equals(percentage, that.percentage) &&
                Objects.equals(cashbackPercentage, that.cashbackPercentage);
    }

}
