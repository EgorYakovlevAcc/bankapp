package com.presentation.demo.model;

import com.presentation.demo.constants.enums.AUTHORITIES;
import com.presentation.demo.model.card.Card;
import com.presentation.demo.service.validation.email.Email;
import com.presentation.demo.service.validation.phonenumber.PhoneNumber;
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
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username can't be empty")
    private String username;

    @Email(message = "Wrong email format!")
    @Column (name = "e_mail")
    private String email;

    @PhoneNumber(message = "Wrong telephone number format!")
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mobile_phone_number_id")
    private MobilePhoneNumber mobilePhoneNumber;

    private String  password;

    private String  passwordConfirmation;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ResetPasswordToken resetPasswordToken;

    @Column(name = "role")
    private String authority;

    @OneToMany(mappedBy = "cardHolder", cascade = CascadeType.ALL)
    private List<Card> cards;

    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private List<Bill> bills;

    private String activationCode;


    public User() {
    }

    public User(String username,String email, MobilePhoneNumber mobilePhoneNumber, String password, String passwordConfirmation,
                List<Bill> bills, List<Card> cards, String authority, String activationCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.bills = bills;
        this.cards = cards;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.authority = authority;
        this.activationCode = activationCode;
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

    public void setAuthority(AUTHORITIES newAuthority){
        authority = newAuthority.getAuthority();
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String registrationCode) {
        this.activationCode = registrationCode;
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
    public boolean equals(Object target) {
        if (this == target) return true;
        if (target == null || getClass() != target.getClass()) return false;
        User user = (User) target;
        return Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(mobilePhoneNumber, user.mobilePhoneNumber);
    }

}
