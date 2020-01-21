package com.presentation.demo.service.mobilephonenumber;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.repository.MobilePhoneNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

@Service
public class MobilePhoneNumberServiceImpl implements MobilePhoneNumberService {

    private Logger SERVICE_LOGGER = LoggerFactory.getLogger(MobilePhoneNumberServiceImpl.class);

    @Autowired
    private MobilePhoneNumberRepository mobilePhoneNumberRepository;

    @Transactional(rollbackForClassName = "ConstraintViolationException")
    @Override
    public void save(MobilePhoneNumber mobilePhoneNumber) {
        try{
            mobilePhoneNumberRepository.save(mobilePhoneNumber);
            mobilePhoneNumberRepository.flush();
        }
        catch (ConstraintViolationException exp){
            SERVICE_LOGGER.info(exp.getLocalizedMessage());
            throw new PersistenceException();
        }
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
    public MobilePhoneNumber findMobilePhoneNumberByMobilePhoneNumberValue(String mobilePhoneNumberValue) {
        return mobilePhoneNumberRepository.findMobilePhoneNumberByMobilePhoneNumberValue(mobilePhoneNumberValue);
    }
}
