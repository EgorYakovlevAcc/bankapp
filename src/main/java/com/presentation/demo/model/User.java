package com.presentation.demo.model;

import com.presentation.demo.constants.enums.ROLES;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.presentation.demo.constants.enums.ROLES.USER;

@Entity
@Table (name = "users")
public class User implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getRole()));
        return authorities;
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

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Username can't be empty")
    private String  username;

    @Column (name = "e_mail")
    @NotEmpty(message = "Email can't be empty")
    private String  email;

    private String  password;

    private String  passwordConfirmation;

    private String role;

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

    public User(@NotEmpty(message = "Username can't be empty") String username, @NotEmpty(message = "Email can't be empty") String email, String password, String passwordConfirmation,List<Bill> bills, List<Card> cards,@NotEmpty(message = "Role can't be empty")String role ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.bills = bills;
        this.cards = cards;
        this.role = role;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(ROLES role) {
        this.role = role.getAuthority();
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
