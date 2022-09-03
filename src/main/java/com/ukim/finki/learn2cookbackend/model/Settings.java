package com.ukim.finki.learn2cookbackend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToMany
    List<Ingredient> filterIngredients;
    @OneToOne(cascade = CascadeType.ALL)
    MealPeriod breakfast;
    @OneToOne(cascade = CascadeType.ALL)
    MealPeriod lunch;
    @OneToOne(cascade = CascadeType.ALL)
    MealPeriod dinner;
    Integer maxCalories;
    boolean isVegan;
}
