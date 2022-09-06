package com.ukim.finki.learn2cookbackend.controller;

import com.ukim.finki.learn2cookbackend.exception.UserNotFound;
import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.User;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientSizeType;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientType;
import com.ukim.finki.learn2cookbackend.request.LoginRequest;
import com.ukim.finki.learn2cookbackend.service.IngredientsService;
import com.ukim.finki.learn2cookbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("login")
    public User login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findUser(loginRequest.getUsername());
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new UserNotFound();
        } else {
            return user;
        }
    }

    @PostMapping("register")
    public User register(@RequestBody LoginRequest loginRequest) {
        return userService.registerUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("{username}")
    public User addIngredient(@PathVariable String username, @RequestBody IngredientWithSize ingredient) {
        User user = userService.findUser(username);

        if (ingredient.getIngredient().getType() == IngredientType.FRIDGE) {
            user.getFridgeItems().add(ingredient);
        } else if (ingredient.getIngredient().getType() == IngredientType.KITCHEN) {
            user.getKitchenItems().add(ingredient);
        }

        return user;
    }

    @GetMapping("ingredients")
    public List<IngredientWithSize> getAllIngredients() {
        List<IngredientWithSize> ingredients = ingredientsService.getAll();

        if(ingredients.isEmpty()) {
            return ingredientsService.saveIngredients(ingredientsService.getMockIngredients())
                    .stream()
                    .map(x->ingredientsService.wrapIngredientWithSize(x,0, IngredientSizeType.X))
                    .toList();
        }

        return ingredientsService.getAll();
    }
}
