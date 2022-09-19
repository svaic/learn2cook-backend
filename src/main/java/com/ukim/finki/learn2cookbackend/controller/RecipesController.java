package com.ukim.finki.learn2cookbackend.controller;

import com.ukim.finki.learn2cookbackend.model.Receipt;
import com.ukim.finki.learn2cookbackend.model.User;
import com.ukim.finki.learn2cookbackend.model.dto.ReceiptDto;
import com.ukim.finki.learn2cookbackend.service.RecipesService;
import com.ukim.finki.learn2cookbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private UserService userService;

    @PostMapping
    public List<ReceiptDto> getAllRecipes(Authentication authentication) {
        User user = userService.findUser(authentication.getName());

        return recipesService.getRecipesForUser(user);
    }

    @PostMapping("save")
    @RolesAllowed("ROLE_ADMIN")
    public Receipt saveRecipes(@RequestBody Receipt receipt) {
        return recipesService.saveReceipt(receipt);
    }
}
