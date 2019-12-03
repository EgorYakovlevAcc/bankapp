package com.presentation.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Entity
@Table (name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Username can't be empty")
    private String  username;

    @Column (name = "e_mail")
    @NotEmpty(message = "Email can't be empty")
    private String  email;

    private String  password;

    private String passwordGoogle;

    @OneToMany(mappedBy = "cardHolder", cascade = CascadeType.ALL)
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private List<Bill> bills;

    public User() {
    }

    public User(@NotEmpty(message = "Username can't be empty") String username, @NotEmpty(message = "Email can't be empty") String email, String password, String passwordGoogle, List<Bill> bills, List<Card> cards) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordGoogle = passwordGoogle;
        this.bills = bills;
        this.cards = cards;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordGoogle() {
        return passwordGoogle;
    }

    public void setPasswordGoogle(String passwordGoogle) {
        this.passwordGoogle = passwordGoogle;
    }
}
