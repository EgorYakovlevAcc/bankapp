package com.presentation.demo.repository;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    Bill findBillById(Integer id);
    List<Bill> findBillsByLastTransaction(Date lastTransaction);
    List<Bill> findBillByHolder(User holder);
    Bill findBillByNumber(String number);
}
