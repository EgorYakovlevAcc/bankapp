package com.presentation.demo.service.validation.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email,String> {

    private String emailPattern;

    @Override
    public void initialize(Email constraintAnnotation) {
        this.emailPattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches(emailPattern,s);
    }
}
