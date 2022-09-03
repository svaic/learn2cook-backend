package com.ukim.finki.learn2cookbackend.controller;

import com.ukim.finki.learn2cookbackend.exception.UserNotFound;
import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientType;
import com.ukim.finki.learn2cookbackend.model.User;
import com.ukim.finki.learn2cookbackend.request.LoginRequest;
import com.ukim.finki.learn2cookbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

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
    public User register(@RequestBody User user) {
        return userService.saveUser(user);
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
}
