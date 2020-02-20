package com.presentation.demo.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "reset_tokens")
public class ResetPasswordToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "resetPasswordToken")
    private User user;

    private String token;

    private Calendar expireDate;

    public ResetPasswordToken() {
    }

    public ResetPasswordToken(String token, User user, Calendar expireDate){
        this.token = token;
        this.user = user;
        this.expireDate = expireDate;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User target) {
        this.user = target;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String tokenName) {
        this.token = tokenName;
    }

    public Calendar getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Calendar expireDate) {
        this.expireDate = expireDate;
    }
}
