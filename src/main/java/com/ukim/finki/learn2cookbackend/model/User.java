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
    List<IngredientWithSize> fridgeItems;

    @ManyToMany(cascade = CascadeType.PERSIST)
    List<IngredientWithSize> kitchenItems;

    @ManyToMany(cascade = CascadeType.ALL)
    List<ReceiptDone> receiptsDone;

    int points;

    @OneToOne(cascade = CascadeType.ALL)
    Settings settings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(type);
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
}
