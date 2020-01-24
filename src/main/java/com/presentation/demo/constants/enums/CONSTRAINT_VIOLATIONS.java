package com.presentation.demo.constants.enums;

public enum CONSTRAINT_VIOLATIONS {

    EMAIL("email"),
    PHONE_lNUMBER("phone_number");

    String violationName;

    CONSTRAINT_VIOLATIONS(String violationName){
        this.violationName = violationName;
    }

    public String getViolationName(){
        return violationName;
    }
}
