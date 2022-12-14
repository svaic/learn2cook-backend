package com.ukim.finki.learn2cookbackend.model;

import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientSizeType;
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
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class IngredientWithSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Ingredient ingredient;
    int count;
    @Enumerated(EnumType.STRING)
    IngredientSizeType sizeType;

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;

        IngredientWithSize ingredientWithSize = (IngredientWithSize) obj;
        return ingredientWithSize.getId().equals(this.getId());
    }
}
