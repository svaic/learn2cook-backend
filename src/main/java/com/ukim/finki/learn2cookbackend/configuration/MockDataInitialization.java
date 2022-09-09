package com.ukim.finki.learn2cookbackend.configuration;

import com.ukim.finki.learn2cookbackend.service.IngredientsService;
import com.ukim.finki.learn2cookbackend.service.RecipesService;
import com.ukim.finki.learn2cookbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MockDataInitialization {
    @Autowired
    private RecipesService recipesService;

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initData() {
        ingredientsService.saveAll(ingredientsService.mockIngredients());
        recipesService.saveAll(recipesService.mockRecipes());
        userService.saveUser(userService.getDefaultUserSettings());
    }
}
