package com.presentation.demo.constants.enums;

import org.springframework.security.core.GrantedAuthority;

public enum ROLES implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN");

    String name;

    ROLES (String name) {
        this.name = name;
    }

//    public String getName() {
//        return name;
//    }
    @Override
    public String toString() {
        return "ROLES{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
