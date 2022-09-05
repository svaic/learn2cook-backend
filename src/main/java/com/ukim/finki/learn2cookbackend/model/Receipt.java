package com.ukim.finki.learn2cookbackend.model;

import com.ukim.finki.learn2cookbackend.model.enumerable.FoodCategory;
import com.ukim.finki.learn2cookbackend.model.enumerable.ReceiptDifficulty;
import com.ukim.finki.learn2cookbackend.model.enumerable.ReceiptType;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
}
