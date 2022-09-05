package com.ukim.finki.learn2cookbackend.service;

import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.enumerable.FoodCategory;
import com.ukim.finki.learn2cookbackend.model.enumerable.ReceiptDifficulty;
import com.ukim.finki.learn2cookbackend.model.enumerable.ReceiptType;
import com.ukim.finki.learn2cookbackend.model.Receipt;
import com.ukim.finki.learn2cookbackend.model.Step;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientSizeType;
import com.ukim.finki.learn2cookbackend.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RecipesService {
    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private ReceiptRepository receiptRepository;

    public List<Receipt> recipes() {
        //List<Receipt> recipes = receiptRepository.findAll();
//        if (recipes.isEmpty()) {
//            return receiptRepository.saveAllAndFlush(new ArrayList<>(mockRecipes()));
//        }
        return mockRecipes();
    }

    public List<Receipt> mockRecipes() {
        Receipt receipt1 = new Receipt();
        receipt1.setName("Macaroni");
        receipt1.setCategory(FoodCategory.MAIN);
        receipt1.setType(ReceiptType.DINNER);
        receipt1.setCalories(2000);
        receipt1.setDifficulty(ReceiptDifficulty.EASY);
        receipt1.setPictureUrl("https://www.ruchiskitchen.com/wp-content/uploads/2022/03/Indian-style-Vegetable-Macaroni-3-500x500.jpg");

        Receipt receipt2 = new Receipt();
        receipt2.setName("Pizza");
        receipt2.setCategory(FoodCategory.MAIN);
        receipt2.setType(ReceiptType.DINNER);
        receipt2.setCalories(2400);
        receipt2.setDifficulty(ReceiptDifficulty.HARD);
        receipt2.setPictureUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg");

        IngredientWithSize macaroniWithSize = new IngredientWithSize();
        macaroniWithSize.setIngredient(ingredientsService.getIngredient("Raw macaroni"));
        macaroniWithSize.setCount(300);
        macaroniWithSize.setSizeType(IngredientSizeType.G);

        IngredientWithSize saltWithSize = new IngredientWithSize();
        saltWithSize.setIngredient(ingredientsService.getIngredient("Salt"));
        saltWithSize.setCount(150);
        saltWithSize.setSizeType(IngredientSizeType.G);

        receipt1.setKitchenEquipment(ingredientsService.getMockKitchenItems());
        receipt2.setKitchenEquipment(ingredientsService.getMockKitchenItems());

        Step step1 = new Step();
        step1.setStepNumber(1);
        step1.setIngredientsUsed(new ArrayList<>(List.of(macaroniWithSize)));
        step1.setDescription("Stavi voda vo ringla");
        step1.setPictureUrl("https://www.thoughtco.com/thmb/r213S4ELSf1XozTXmITBspeLz6Y=/1333x1000/smart/filters:no_upscale()/BoilingWater-58e3d1ac5f9b58ef7e060f93.jpg");
        step1.setDuration(Duration.ofMinutes(15));

        Step step2 = new Step();
        step2.setStepNumber(2);
        step2.setIngredientsUsed(new ArrayList<>(List.of(saltWithSize)));
        step2.setDescription("Stavi sol vo tafceto");
        step2.setPictureUrl("https://media.gettyimages.com/photos/salt-being-sprinkled-into-pot-of-water-picture-id162758559?s=2048x2048");
        step2.setDuration(Duration.ofSeconds(5));

        Step step3 = new Step();
        step3.setStepNumber(3);
        step3.setIngredientsUsed(new ArrayList<>());
        step3.setDescription("Iskluci ja ringlata");
        step3.setPictureUrl("https://thumbs.dreamstime.com/z/turn-oven-dial-wit-hnd-hand-close-heat-meat-lunch-dinner-food-preparation-ingredient-cooking-chef-house-family-ready-gourmet-139076178.jpg");
        step3.setDuration(Duration.ZERO);

        List<Step> steps = new ArrayList<>(List.of(step1, step2, step3));

        receipt1.setSteps(steps);
        receipt1.setIngredients(ingredientsService.sumOfIngredientsWithSize(steps));
        receipt1.setTimeToPrepare(Duration.ofMinutes(30));

        receipt2.setSteps(steps);
        receipt2.setIngredients(ingredientsService.sumOfIngredientsWithSize(steps));
        receipt2.setTimeToPrepare(Duration.ofMinutes(30));

        List<Receipt> mockReceipts = Stream.concat(
                Collections.nCopies(5, receipt1).stream(),
                Collections.nCopies(5, receipt2).stream())
                .collect(Collectors.toList());

        Collections.shuffle(mockReceipts);
        return mockReceipts;
    }

    public Receipt saveReceipt(Receipt receipt) {
        return receiptRepository.saveAndFlush(receipt);
    }
}
