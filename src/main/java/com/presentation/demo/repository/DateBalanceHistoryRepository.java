package com.presentation.demo.repository;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.DateBalanceHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateBalanceHistoryRepository extends JpaRepository<DateBalanceHistory,Integer> {
    DateBalanceHistory findDateBalanceHistoryById(Integer id);
    List<DateBalanceHistory> findDateBalanceHistoriesByTargetBill(Bill bill);
}
