package com.presentation.demo.repository;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.Card;
import com.presentation.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Card findCardById(Integer id);
    List<Card> findCardsByBill(Bill bill);
    List<Card> findCardsByCardHolder(User cardHolder);
}