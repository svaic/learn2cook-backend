package com.ukim.finki.learn2cookbackend.controller;

import com.ukim.finki.learn2cookbackend.exception.UserNotFound;
import com.ukim.finki.learn2cookbackend.model.Ingredient;
import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.User;
import com.ukim.finki.learn2cookbackend.model.request.ChangeUserIngredientRequest;
import com.ukim.finki.learn2cookbackend.model.request.LoginRequest;
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
        }

        return user;
    }

    @PostMapping("register")
    public User register(@RequestBody LoginRequest loginRequest) {
        return userService.registerUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("changeIngredients/{username}")
    public User changeIngredientState(@PathVariable String username, @RequestBody ChangeUserIngredientRequest request) {
        User user = userService.findUser(username);
        return userService.changeIngredients(user, request.getIngredient(), request.isAddIngredient());
    }

    @GetMapping("ingredientsSize")
    public List<IngredientWithSize> getAllIngredientsWithSize() {
        return ingredientsService.getAllWithSize();
    }

    @GetMapping("ingredients")
    public List<Ingredient> getAllIngredients() {
        return ingredientsService.getAll();
    }
}
