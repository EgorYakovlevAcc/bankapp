package com.presentation.demo.service.validation.phonenumber;

import com.presentation.demo.model.MobilePhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, MobilePhoneNumber> {

    private String phoneNumberPattern;

    @Override
    public void initialize(PhoneNumber phoneNumber) {
        this.phoneNumberPattern = phoneNumber.pattern();
    }

    @Override
    public boolean isValid(MobilePhoneNumber s, ConstraintValidatorContext constraintValidatorContext){
        String mobilePhoneNumberValue = s.getMobilePhoneNumberValue();
        return Pattern.matches(phoneNumberPattern,mobilePhoneNumberValue);
    }
}
