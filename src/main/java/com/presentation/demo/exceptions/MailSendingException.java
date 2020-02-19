package com.presentation.demo.exceptions;

import org.springframework.mail.MailException;

public class MailSendingException extends MailException {

    private String email;

    public MailSendingException(String msg) {
        super(msg);
    }

    public MailSendingException(String email, String msg) {
        super(msg);
        this.email = email;
    }

    public MailSendingException(String email, String msg, Throwable cause) {
        super(msg, cause);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
