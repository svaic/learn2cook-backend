package com.ukim.finki.learn2cookbackend.controller;

import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.Receipt;
import com.ukim.finki.learn2cookbackend.service.IngredientsService;
import com.ukim.finki.learn2cookbackend.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    @GetMapping
    public List<Receipt> getAllRecipes() {
        return recipesService.recipes();
    }

    @GetMapping("save")
    public Receipt saveRecipes() {
        Receipt newReceipt = recipesService.recipes().get(0);
        return recipesService.saveReceipt(newReceipt);
    }
}
