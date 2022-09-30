package com.ukim.finki.learn2cookbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ukim.finki.learn2cookbackend.model.enumerable.UserType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "userEntity")
public class User implements UserDetails {
    @Id
    String username;

    @JsonIgnore
    String password;

    UserType type;

    @ManyToMany(cascade = CascadeType.PERSIST)
    List<Ingredient> items;

    @ManyToMany(cascade = CascadeType.ALL)
    List<ReceiptDone> receiptsDone;

    int points;

    boolean hasArchivedCertificate;

    @OneToOne(cascade = CascadeType.ALL)
    Settings settings;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(type);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
