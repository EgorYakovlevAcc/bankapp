package com.presentation.demo.constants;

public final class Params {

    public static final int RANDOM_PASSWORD_LENGTH = 30;
    public static final long TEN_DAYS_SEC = 36000000;
    public static final long ONE_MINUTE_SEC = 60000;
    public static final int  TRANSACTION_EXECUTOR_DELAY = 1000;

    //for regex
    public static final String PHONE_NUMBER_PATTERN = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}|\\d{11}|\\+\\d{11}";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    //for admin
    public static final String ADMIN_NAME = "SYSADMIN";
    public static final String ADMIN_MOBILE_PHONE_NUMBER = "89779730970";
    public static final String ADMIN_EMAIL = "admin@mail.ru";

    //for exchange-rate parser
    public static final String CB_URL = "https://www.cbr.ru/eng/currency_base/daily/";
    public static final String DEFAULT_REFERRER = "https://google.com";
    public static final Integer DEFAULT_TIMEOUT = 20000;
    public static final Boolean FOLLOW_REDIRECTS = false;
    public static final String EXCHANGE_RATE_TAG = "table.data";

    private Params() {}

}
