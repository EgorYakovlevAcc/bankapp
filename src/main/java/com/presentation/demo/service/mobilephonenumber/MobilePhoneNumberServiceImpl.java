package com.presentation.demo.service.mobilephonenumber;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.MobilePhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobilePhoneNumberServiceImpl implements MobilePhoneNumberService {

    @Autowired
    private MobilePhoneNumberRepository mobilePhoneNumberRepository;

    @Override
    public void save(MobilePhoneNumber mobilePhoneNumber) {
        mobilePhoneNumberRepository.save(mobilePhoneNumber);
    }

    @Override
    public void delete(MobilePhoneNumber mobilePhoneNumber) {
        mobilePhoneNumberRepository.delete(mobilePhoneNumber);
    }

    @Override
    public MobilePhoneNumber findMobilePhoneNumberById(Integer id) {
        return mobilePhoneNumberRepository.findMobilePhoneNumberById(id);
    }

    @Override
    public MobilePhoneNumber findMobilePhoneNumberByOwner(User owner) {
        return mobilePhoneNumberRepository.findMobilePhoneNumberByOwner(owner);
    }
}
