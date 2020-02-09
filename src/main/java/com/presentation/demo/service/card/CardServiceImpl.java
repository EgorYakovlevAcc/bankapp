package com.presentation.demo.service.card;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;
import com.presentation.demo.model.card.Card;
import com.presentation.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void save(Card card) { cardRepository.save(card); }

    @Override
    public void delete(Card card) { cardRepository.delete(card); }

    @Override
    public Card findCardById(Integer id) { return cardRepository.findCardById(id); }

    @Override
    @Transactional
    public List<Card> findCardsByBill(Bill bill) { return cardRepository.findCardsByBill(bill); }

    @Override
    @Transactional
    public List<Card> findCardsByCartHolder(User cartHolder) { return cardRepository.findCardsByCardHolder(cartHolder); }
}
