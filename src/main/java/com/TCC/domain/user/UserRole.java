package com.TCC.domain.user;

public enum UserRole {
    ADMIN("admin"),
    CUSTOMER("customer"),
    COMPANY("company");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    String getRole(){
        return this.role;
    }
}
