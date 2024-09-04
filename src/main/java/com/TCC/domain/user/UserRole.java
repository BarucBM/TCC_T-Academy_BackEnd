package com.TCC.domain.user;

public enum UserRole {

    GUEST("guest"),

    ADMIN("admin"),

    CUSTOMER("customer"),

    COMPANY("company");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    String getRole(){
        return this.role;
    }
}
