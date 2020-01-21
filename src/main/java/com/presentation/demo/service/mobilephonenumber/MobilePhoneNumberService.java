package com.presentation.demo.service.mobilephonenumber;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;

public interface MobilePhoneNumberService {
    void save(MobilePhoneNumber mobilePhoneNumber);
    void delete(MobilePhoneNumber mobilePhoneNumber);
    MobilePhoneNumber findMobilePhoneNumberById(Integer id);
    MobilePhoneNumber findMobilePhoneNumberByMobilePhoneNumberValue(String mobilePhoneNumberValue);
}
