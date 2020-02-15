package com.presentation.demo.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "reset_tokens")
public class ResetPasswordToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)//todo:one to one doesn't work?
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
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
