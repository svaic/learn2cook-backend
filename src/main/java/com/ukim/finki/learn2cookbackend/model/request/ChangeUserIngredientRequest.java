package com.ukim.finki.learn2cookbackend.model.request;

import com.ukim.finki.learn2cookbackend.model.Ingredient;
import lombok.Getter;

@Getter
public class ChangeUserIngredientRequest {
    Ingredient ingredient;
    boolean addIngredient;
}
