package com.ukim.finki.learn2cookbackend.model.enumerable;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority {
    DEFAULT,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
