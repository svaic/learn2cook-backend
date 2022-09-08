package com.ukim.finki.learn2cookbackend.model;

import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String pictureUrl;
    @Enumerated(EnumType.STRING)
    IngredientType type;

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;

        Ingredient ingredient = (Ingredient) obj;
        return ingredient.getId().equals(this.getId());
    }
}
