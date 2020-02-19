package com.presentation.demo.service.bill;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl implements BillService{

    @Autowired
    private BillRepository billRepository;

    @Override
    public void save(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public void delete(Bill bill) {
        billRepository.delete(bill);
    }

    @Override
    public Bill findBillById(Integer id) {
        return billRepository.findBillById(id);
    }

    @Override
    public Bill findBillByNumber(String number) {
        return billRepository.findBillByNumber(number);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public List<Bill> findBillsByLastTransaction(Date date) {
        return billRepository.findBillsByLastTransaction(date);
    }

    @Override
    public List<Bill> findBillsByHolder(User holder) {
        return billRepository.findBillByHolder(holder);
    }

    @Override
    public List<Bill> findBillsByDueBefore(Date date) {
        return billRepository.findBillsByDueBefore(date);
    }
}
