package com.presentation.demo.constants;

public final class Constant {
    public static final int RANDOM_PASSWORD_LENGTH = 30;
    public static final long TEN_DAYS_SEC = 36000000;
    public static final long ONE_MINUTE_SEC = 60000;

    //for regex
    public static final String PHONE_NUMBER_PATTERN = "^(?:(?:\\+|00)(\\d{1,3})[\\s-]?)?(\\d{10})$";
    private Constant() {}

    //for admin
    public static final String ADMIN_NAME = "SYSADMIN";
    public static final String ADMIN_MOBILE_PHONE_NUMBER = "+7-977-973-09-70";
    public static final String ADMIN_EMAIL = "admin@mail.ru";

}
