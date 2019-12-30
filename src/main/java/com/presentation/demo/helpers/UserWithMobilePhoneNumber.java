package com.presentation.demo.helpers;

import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;

public class UserWithMobilePhoneNumber {

    private User user;

    private MobilePhoneNumber mobilePhoneNumber;

    public UserWithMobilePhoneNumber() {
    }

    public UserWithMobilePhoneNumber(User user,MobilePhoneNumber mobilePhoneNumber) {
        this.user = user;
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MobilePhoneNumber getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(MobilePhoneNumber mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

}
