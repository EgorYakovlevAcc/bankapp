package com.presentation.demo.service.bill;

import com.presentation.demo.model.Bill;

import java.util.Date;
import java.util.List;

public interface BillService {

    void save(Bill bill);

    void delete(Bill bill);

    Bill findBillById(Integer id);

    List<Bill> findBillsByLastTransaction(Date date);
}
