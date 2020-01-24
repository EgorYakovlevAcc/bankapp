package com.presentation.demo.errors.persistance;

import com.presentation.demo.constants.enums.CONSTRAINT_VIOLATIONS;

import javax.persistence.PersistenceException;

public class ConstraintViolationException extends PersistenceException {

    private CONSTRAINT_VIOLATIONS constraintViolation;

    ConstraintViolationException(CONSTRAINT_VIOLATIONS constraintViolation){
        this.constraintViolation = constraintViolation;
    }

    public CONSTRAINT_VIOLATIONS getViolation(){
        return constraintViolation;
    }

}
