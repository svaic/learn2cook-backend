package com.ukim.finki.learn2cookbackend.model;

import com.ukim.finki.learn2cookbackend.model.enumerable.UserType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "userEntity")
public class User {
    @Id
    String username;
    String password;
    UserType type;
    @ManyToMany(cascade = CascadeType.ALL)
    List<IngredientWithSize> fridgeItems;
    @ManyToMany(cascade = CascadeType.ALL)
    List<IngredientWithSize> kitchenItems;
    @ManyToMany(cascade = CascadeType.ALL)
    List<ReceiptDone> receiptsDone;
    int points;
    @OneToOne(cascade = CascadeType.ALL)
    Settings settings;
}
