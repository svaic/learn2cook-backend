package com.ukim.finki.learn2cookbackend.controller;

import com.ukim.finki.learn2cookbackend.model.Receipt;
import com.ukim.finki.learn2cookbackend.model.User;
import com.ukim.finki.learn2cookbackend.model.dto.ReceiptDto;
import com.ukim.finki.learn2cookbackend.model.request.LoginRequest;
import com.ukim.finki.learn2cookbackend.service.RecipesService;
import com.ukim.finki.learn2cookbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    @Autowired
    private UserService userService;

    @PostMapping
    public List<ReceiptDto> getAllRecipes(@RequestBody LoginRequest loginRequest) {
        User user = userService.findUser(loginRequest.getUsername());

        return recipesService.getRecipesForUser(user);
    }

    @GetMapping("save")
    public Receipt saveRecipes(@RequestBody Receipt receipt) {
        return recipesService.saveReceipt(receipt);
    }
}
