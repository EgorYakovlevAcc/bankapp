package com.presentation.demo.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static com.presentation.demo.constants.Constant.PHONE_NUMBER_PATTERN;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String> {

    @Override
    public void initialize(PhoneNumber phoneNumber) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext arg1) {
        return Pattern.matches(PHONE_NUMBER_PATTERN,s);
    }
}
