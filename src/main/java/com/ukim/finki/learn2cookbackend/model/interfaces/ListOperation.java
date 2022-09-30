package com.ukim.finki.learn2cookbackend.model.interfaces;

import com.ukim.finki.learn2cookbackend.model.Ingredient;

import java.util.List;

public interface ListOperation {
    void apply(List<Ingredient> list, Ingredient ingredient);
}