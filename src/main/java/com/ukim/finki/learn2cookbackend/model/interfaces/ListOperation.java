package com.ukim.finki.learn2cookbackend.model.interfaces;

import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;

import java.util.List;

public interface ListOperation {
    void apply(List<IngredientWithSize> list, IngredientWithSize ingredient);
}