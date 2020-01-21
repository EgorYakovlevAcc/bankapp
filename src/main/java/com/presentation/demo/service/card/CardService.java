package com.presentation.demo.service.card;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.Card;
import com.presentation.demo.model.User;

import java.util.List;

public interface CardService {
    void save(Card card);
    void delete(Card card);
    Card findCardById(Integer id);
    List<Card> findCardsByBill(Bill bill);
    List<Card> findCardsByCartHolder(User cartHolder);
}
