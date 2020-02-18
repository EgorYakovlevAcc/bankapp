package com.presentation.demo.service.bill;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;

import java.util.Date;
import java.util.List;

public interface BillService {

    void save(Bill bill);

    void delete(Bill bill);

    Bill findBillById(Integer id);

    Bill findBillByNumber(String number);

    List<Bill> findBillsByLastTransaction(Date date);

    List<Bill> findBillsByHolder(User holder);

    List<Bill> findBillsByDueBefore(Date date);
}
