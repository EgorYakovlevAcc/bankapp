package com.presentation.demo.service.bill;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.BillRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<Bill> findBillsByLastTransaction(Date date) {
        return billRepository.findBillsByLastTransaction(date);
    }

    @Override
    @Transactional
    public List<Bill> findBillsByHolder(User holder) {
        return billRepository.findBillByHolder(holder);
    }
}
