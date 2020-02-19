package com.presentation.demo.repository;

import com.presentation.demo.model.MobilePhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobilePhoneNumberRepository extends JpaRepository<MobilePhoneNumber,Integer> {
    MobilePhoneNumber findMobilePhoneNumberById(Integer id);
    MobilePhoneNumber findMobilePhoneNumberByMobilePhoneNumberValue(String mobilePhoneNumberValue);
}
