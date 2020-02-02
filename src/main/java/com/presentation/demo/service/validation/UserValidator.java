package com.presentation.demo.service.validation;

import com.presentation.demo.model.User;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import com.presentation.demo.service.user.UserServiceImpl;
import com.presentation.demo.service.validation.email.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public class UserValidator implements org.springframework.validation.Validator {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private javax.validation.Validator javaxValidator;

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        Set<ConstraintViolation<User>> javaxValidates = javaxValidator.validate(user);

        for (ConstraintViolation<User> constraintViolation : javaxValidates) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }

        if (userService.findUserByEmail(user.getEmail()) != null){
            errors.rejectValue("email","","This email is already been used!");
        }
        else if (userService.findUserByUsername(user.getUsername()) != null){
            errors.rejectValue("username","","User with this username already exists!");
        }
        else if (mobilePhoneNumberService.findMobilePhoneNumberByMobilePhoneNumberValue(user.getMobilePhoneNumber().getMobilePhoneNumberValue()) != null){//one to one
            errors.rejectValue("mobilePhoneNumber","","This phone number is already been used!");
        }
    }
}
