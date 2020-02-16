package com.presentation.demo.service.datebalancehistory;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.DateBalanceHistory;
import com.presentation.demo.repository.DateBalanceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class DateBalanceHistoryServiceImpl implements DateBalanceHistoryService {

    @Autowired
    private DateBalanceHistoryRepository dateBalanceHistoryRepository;

    @Override
    public DateBalanceHistory findDateBalanceHistoryById(Integer id) {
        return dateBalanceHistoryRepository.findDateBalanceHistoryById(id);
    }

    @Override
    @Transactional
    public List<DateBalanceHistory> findDateBalanceHistoriesByBill(Bill bill) {
        return dateBalanceHistoryRepository.findDateBalanceHistoriesByTargetBill(bill);
    }

    @Override
    public void createNewDateBalanceHistory(Bill bill) {
        DateBalanceHistory dateBalanceHistory = new DateBalanceHistory();
        dateBalanceHistory.setTargetBill(bill);
        dateBalanceHistory.setBalance(bill.getBalance());
        dateBalanceHistory.setDate(new Date());
        save(dateBalanceHistory);
    }

    @Override
    public void save(DateBalanceHistory dateBalanceHistory) {
        dateBalanceHistoryRepository.save(dateBalanceHistory);
    }

    @Transactional
    @Override
    public void delete(DateBalanceHistory dateBalanceHistory) {
        dateBalanceHistoryRepository.delete(dateBalanceHistory);
    }
}
