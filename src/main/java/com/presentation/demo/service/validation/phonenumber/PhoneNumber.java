package com.presentation.demo.service.validation.phonenumber;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.presentation.demo.constants.Params.PHONE_NUMBER_PATTERN;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface PhoneNumber {
    String pattern() default PHONE_NUMBER_PATTERN;
    String message() default "Wrong phone number!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}