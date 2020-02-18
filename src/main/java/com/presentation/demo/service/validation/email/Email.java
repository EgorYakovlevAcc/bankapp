package com.presentation.demo.service.validation.email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.presentation.demo.constants.Properties.EMAIL_PATTERN;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface Email {
    String pattern() default EMAIL_PATTERN;
    String message() default "Wrong email format!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
