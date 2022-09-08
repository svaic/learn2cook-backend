package com.ukim.finki.learn2cookbackend.request;

import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import lombok.Getter;

@Getter
public class ChangeUserIngredientRequest {
    IngredientWithSize ingredient;
    boolean addIngredient;
}
