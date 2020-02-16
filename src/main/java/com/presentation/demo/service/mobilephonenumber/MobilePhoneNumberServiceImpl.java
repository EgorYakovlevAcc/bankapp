package com.presentation.demo.service.mobilephonenumber;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.repository.MobilePhoneNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MobilePhoneNumberServiceImpl implements MobilePhoneNumberService {

    private Logger SERVICE_LOGGER = LoggerFactory.getLogger(MobilePhoneNumberServiceImpl.class);

    @Autowired
    private MobilePhoneNumberRepository mobilePhoneNumberRepository;

    @Override
    public void save(MobilePhoneNumber mobilePhoneNumber) {
        mobilePhoneNumberRepository.save(mobilePhoneNumber);
    }

    @Transactional
    @Override
    public void delete(MobilePhoneNumber mobilePhoneNumber) {
        mobilePhoneNumberRepository.delete(mobilePhoneNumber);
    }

    @Override
    public MobilePhoneNumber findMobilePhoneNumberById(Integer id) {
        return mobilePhoneNumberRepository.findMobilePhoneNumberById(id);
    }

    @Override
    public MobilePhoneNumber findMobilePhoneNumberByMobilePhoneNumberValue(String mobilePhoneNumberValue) {
        return mobilePhoneNumberRepository.findMobilePhoneNumberByMobilePhoneNumberValue(mobilePhoneNumberValue);
    }
}
