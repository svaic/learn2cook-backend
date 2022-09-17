package com.ukim.finki.learn2cookbackend.model;

import com.ukim.finki.learn2cookbackend.model.enumerable.FoodCategory;
import com.ukim.finki.learn2cookbackend.model.enumerable.ReceiptDifficulty;
import com.ukim.finki.learn2cookbackend.model.enumerable.ReceiptType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @Enumerated(EnumType.STRING)
    FoodCategory category;

    @ManyToMany(cascade=CascadeType.ALL)
    List<IngredientWithSize> ingredients;

    @ManyToMany(cascade=CascadeType.ALL)
    List<IngredientWithSize> kitchenEquipment;

    @OneToMany(cascade=CascadeType.ALL)
    List<Step> steps;

    String pictureUrl;

    Duration timeToPrepare;

    @Enumerated(EnumType.STRING)
    ReceiptDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    ReceiptType type;

    int calories;

    int points;
}
