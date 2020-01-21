package com.presentation.demo.model;

import com.presentation.demo.constants.enums.AUTHORITIES;
import com.presentation.demo.service.validation.email.Email;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Username can't be empty")
    private String username;

//    @Email(pattern = "Wrong email format!")
    @Column (name = "e_mail")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mobile_phone_number_id",referencedColumnName ="id")
    private MobilePhoneNumber mobilePhoneNumber;

    private String  password;

    private String  passwordConfirmation;

    private String authority;

    @OneToMany(mappedBy = "cardHolder", cascade = CascadeType.ALL)
    private List<Card> cards;

    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private List<Bill> bills;

    public User() {
    }

    public User(@NotEmpty(message = "Username can't be empty") String username, @NotEmpty(message = "Email can't be empty") String email,@NotEmpty(message = "Phone number can't be empty") MobilePhoneNumber mobilePhoneNumber, String password, String passwordConfirmation,List<Bill> bills, List<Card> cards) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.bills = bills;
        this.cards = cards;
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MobilePhoneNumber getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(MobilePhoneNumber mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setAuthority(AUTHORITIES newAuthority){
        authority = newAuthority.getAuthority();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "id:" + id + "\nusername:" + username;
    }

    @Override
    public boolean equals(Object user) {
        if (user instanceof User) {
            return id.equals(((User) user).getId());
        } else {
            return false;
        }
    }
}
