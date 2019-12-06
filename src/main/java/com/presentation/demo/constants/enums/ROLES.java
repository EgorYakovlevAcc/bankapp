package com.presentation.demo.constants.enums;

public enum ROLES {
    USER("USER"),
    ADMIN("ADMIN");
    String name;

    ROLES (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ROLES{" +
                "name='" + name + '\'' +
                '}';
    }
}
