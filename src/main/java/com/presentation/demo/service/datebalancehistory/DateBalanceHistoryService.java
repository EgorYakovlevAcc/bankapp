package com.presentation.demo.service.datebalancehistory;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.DateBalanceHistory;

import java.util.List;

public interface DateBalanceHistoryService {
    DateBalanceHistory findDateBalanceHistoryById(Integer id);
    List<DateBalanceHistory> findDateBalanceHistoriesByBill(Bill bill);
    void createNewDateBalanceHistory(Bill bill);
    void save(DateBalanceHistory dateBalanceHistory);
    void delete(DateBalanceHistory dateBalanceHistory);
}
