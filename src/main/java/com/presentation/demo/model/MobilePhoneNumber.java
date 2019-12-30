package com.presentation.demo.model;

import com.presentation.demo.annotations.PhoneNumber;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.regex.Pattern;

@Entity
@Table(name = "phone_numbers")
public class MobilePhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user",nullable = true)
    private User owner;

//    @PhoneNumber
    private String mobilePhoneNumber;

    public MobilePhoneNumber() {}

    public MobilePhoneNumber(User owner) {
        this.owner = owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public Integer getId() {
        return id;
    }
}
