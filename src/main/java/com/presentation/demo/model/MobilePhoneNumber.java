package com.presentation.demo.model;

import com.presentation.demo.service.validation.phonenumber.PhoneNumber;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phone_numbers")
public class MobilePhoneNumber {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(mappedBy = "mobilePhoneNumber",cascade = CascadeType.ALL)
    private User owner;

    private String mobilePhoneNumberValue;

    public MobilePhoneNumber() {}

    public MobilePhoneNumber(User owner, String mobilePhoneNumber) {
        this.owner = owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setMobilePhoneNumberValue(String mobilePhoneNumber) {
        this.mobilePhoneNumberValue = mobilePhoneNumber;
    }

    public String getMobilePhoneNumberValue() {
        return mobilePhoneNumberValue;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object target) {
        if (this == target) return true;
        if (target == null || getClass() != target.getClass()) return false;
        MobilePhoneNumber that = (MobilePhoneNumber) target;
        return Objects.equals(this.mobilePhoneNumberValue, that.mobilePhoneNumberValue);
    }

}
