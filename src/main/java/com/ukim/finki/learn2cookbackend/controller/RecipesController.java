package com.ukim.finki.learn2cookbackend.controller;

import com.ukim.finki.learn2cookbackend.model.Receipt;
import com.ukim.finki.learn2cookbackend.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    @PostMapping
    public List<Receipt> getAllRecipes() {
        return recipesService.getRecipes();
    }

    @PostMapping("save")
    @RolesAllowed("ROLE_ADMIN")
    public Receipt saveRecipes(@RequestBody Receipt receipt) {
        return recipesService.saveReceipt(receipt);
    }
}
