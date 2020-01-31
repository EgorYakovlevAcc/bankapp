package com.presentation.demo.service.validation;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class MobilePhoneNumberValidator implements Validator {

    private Logger validatorLogger = LoggerFactory.getLogger(MobilePhoneNumberValidator.class);

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Override
    public boolean supports(Class<?> aClass) {
        return MobilePhoneNumber.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MobilePhoneNumber mobilePhoneNumber = (MobilePhoneNumber) target;
        if (mobilePhoneNumberService.findMobilePhoneNumberByMobilePhoneNumberValue(mobilePhoneNumber.getMobilePhoneNumberValue()) != null){
            errors.rejectValue("mobilePhoneNumberValue","","This mobilePhoneNumberValue is already been used!");
        }
    }
}
