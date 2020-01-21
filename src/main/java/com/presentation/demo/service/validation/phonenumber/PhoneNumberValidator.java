package com.presentation.demo.service.validation.phonenumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String> {

    private String phoneNumberPattern;

    @Override
    public void initialize(PhoneNumber phoneNumber) {
        this.phoneNumberPattern = phoneNumber.pattern();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext){
       return Pattern.matches(phoneNumberPattern,s);
    }
}
