package com.presentation.demo.constants.enums;

import org.springframework.security.core.GrantedAuthority;

public enum AUTHORITIES implements GrantedAuthority {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    String authorityName;

    AUTHORITIES(String authority) {
        this.authorityName = authority;
    }

    @Override
    public String getAuthority() {
        return authorityName;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "name='" + authorityName + '\'' +
                '}';
    }

}
