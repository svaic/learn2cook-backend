package com.ukim.finki.learn2cookbackend.service;

import com.ukim.finki.learn2cookbackend.model.Ingredient;
import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.Step;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientSizeType;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientType;
import com.ukim.finki.learn2cookbackend.repository.IngredientRepository;
import com.ukim.finki.learn2cookbackend.repository.IngredientWithSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientsService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientWithSizeRepository ingredientWithSizeRepository;

    public Ingredient getIngredient(String name) {
        return ingredientRepository.findByName(name)
                .orElse(null);
    }

    public List<Ingredient> getKitchenItems() {
        return ingredientRepository.findByType(IngredientType.KITCHEN);
    }

    public List<IngredientWithSize> getAllWithSize() {
        return ingredientWithSizeRepository.findAll();
    }

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public List<Ingredient> saveAll(List<Ingredient> ingredients) {
        return ingredientRepository.saveAll(ingredients);
    }

    public List<IngredientWithSize> sumOfIngredientsWithSize(List<Step> steps) {
        Map<String, Optional<IngredientWithSize>> distinctIngredients = steps.stream()
                .flatMap(x -> x.getIngredientsUsed().stream())
                .collect(
                        Collectors.groupingBy((x->x.getIngredient().getName()),
                                Collectors.reducing(this::addIngredient))
                );

        return distinctIngredients.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList());
    }

    public IngredientWithSize wrapIngredientWithSize(Ingredient ingredient, int count, IngredientSizeType type) {
        IngredientWithSize ingredientWithSize = new IngredientWithSize();

        ingredientWithSize.setIngredient(ingredient);
        ingredientWithSize.setCount(count);
        ingredientWithSize.setSizeType(type);

        return ingredientWithSize;
    }

    private IngredientWithSize addIngredient(IngredientWithSize a, IngredientWithSize b) {
        a.setCount(a.getCount() + b.getCount());
        return a;
    }

    public List<Ingredient> mockIngredients() {
        String[] ingredientNames = new String[]{"Cheese", "Breed", "Pizza", "Cooking Oil", "Chicken", "Rice", "Potatoes", "Ketchup", "Mayonnaise", "Pasta"};
        List<Ingredient> ingredients = Arrays.stream(ingredientNames).map(x -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(x);
            ingredient.setType(IngredientType.FRIDGE);
            ingredient.setPictureUrl("");

            return ingredient;
        }).collect(Collectors.toList());

        Ingredient rawMacaroniIngredient = new Ingredient();
        rawMacaroniIngredient.setName("Raw macaroni");
        rawMacaroniIngredient.setPictureUrl("https://midnightdelivery.ch/wp-content/uploads/2022/05/MidnightDelivery_WallisellenZuerich_Kraft_Maccaroni_-Cheese.png");
        rawMacaroniIngredient.setType(IngredientType.FRIDGE);

        Ingredient saltIngredient = new Ingredient();
        saltIngredient.setName("Salt");
        saltIngredient.setPictureUrl("https://www.wissenschaft.de/wp-content/uploads/1/8/18-01-15-salz.jpg");
        saltIngredient.setType(IngredientType.FRIDGE);

        ingredients.add(rawMacaroniIngredient);
        ingredients.add(saltIngredient);

        Ingredient pot = new Ingredient();
        pot.setName("pot");
        pot.setPictureUrl("https://www.ikea.com/ch/en/images/products/annons-pot-with-lid-glass-stainless-steel__0714768_pe730240_s5.jpg");
        pot.setType(IngredientType.KITCHEN);

        Ingredient grater = new Ingredient();
        grater.setName("grater");
        grater.setPictureUrl("https://www.ikea.com/ch/en/images/products/idealisk-grater-stainless-steel__0713134_pe729282_s5.jpg?f=xs");
        grater.setType(IngredientType.KITCHEN);

        Ingredient stove = new Ingredient();
        stove.setName("stove");
        stove.setPictureUrl("https://m.media-amazon.com/images/S/aplus-media/vc/96d54909-1f79-43bf-8beb-136a6d56aaf9.__CR236,270,4713,2915_PT0_SX970_V1___.jpg");
        stove.setType(IngredientType.KITCHEN);

        ingredients.add(pot);
        ingredients.add(grater);
        ingredients.add(stove);

        return ingredients;
    }
}
