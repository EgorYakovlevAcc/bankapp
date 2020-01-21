package com.presentation.demo.constants;

public final class Constant {
    public static final int RANDOM_PASSWORD_LENGTH = 30;
    public static final long TEN_DAYS_SEC = 36000000;
    public static final long ONE_MINUTE_SEC = 60000;

    //for regex
    public static final String PHONE_NUMBER_PATTERN = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}|\\d{11}|\\+\\d{11}";
    public static final String EMAIL_PATTERN = "[\\d]+@.[\\d]+\\d{(2|3)}";

    //for admin
    public static final String ADMIN_NAME = "SYSADMIN";
    public static final String ADMIN_MOBILE_PHONE_NUMBER = "89779730970";
    public static final String ADMIN_EMAIL = "admin@mail.ru";

    private Constant() {}

}
