package com.ukim.finki.learn2cookbackend.service;

import com.ukim.finki.learn2cookbackend.exception.ReceiptNotFoundException;
import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.Receipt;
import com.ukim.finki.learn2cookbackend.model.Step;
import com.ukim.finki.learn2cookbackend.model.User;
import com.ukim.finki.learn2cookbackend.model.dto.ReceiptDto;
import com.ukim.finki.learn2cookbackend.model.enumerable.FoodCategory;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientSizeType;
import com.ukim.finki.learn2cookbackend.model.enumerable.ReceiptDifficulty;
import com.ukim.finki.learn2cookbackend.model.enumerable.ReceiptType;
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

    public List<ReceiptDto> getRecipesForUser(User user) {

        List<Receipt> recipes = receiptRepository.findAll();

        List<IngredientWithSize> ingredientsOfUser = new ArrayList<>();
        ingredientsOfUser.addAll(user.getFridgeItems());
        ingredientsOfUser.addAll(user.getKitchenItems());

        recipes = shuffleRecipes(recipes);

        return recipes
                .stream()
                .map(x -> calcReceiptForUser(ingredientsOfUser, x))
                .collect(Collectors.toList());
    }

    public Receipt getReceipt(Long receiptId) {
        return receiptRepository.findById(receiptId).orElseThrow(ReceiptNotFoundException::new);
    }

    public Receipt saveReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    public List<Receipt> saveAll(List<Receipt> recipes) {
        return receiptRepository.saveAll(recipes);
    }

    public List<Receipt> mockRecipes() {
        Receipt receipt1 = new Receipt();
        receipt1.setName("Macaroni");
        receipt1.setCategory(FoodCategory.MAIN);
        receipt1.setType(ReceiptType.DINNER);
        receipt1.setCalories(2000);
        receipt1.setDifficulty(ReceiptDifficulty.EASY);
        receipt1.setPictureUrl("https://www.ruchiskitchen.com/wp-content/uploads/2022/03/Indian-style-Vegetable-Macaroni-3-500x500.jpg");
        receipt1.setPoints(50);

        Receipt receipt2 = new Receipt();
        receipt2.setName("Pizza");
        receipt2.setCategory(FoodCategory.MAIN);
        receipt2.setType(ReceiptType.DINNER);
        receipt2.setCalories(2400);
        receipt2.setDifficulty(ReceiptDifficulty.HARD);
        receipt2.setPictureUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg");
        receipt2.setPoints(80);

        IngredientWithSize macaroniWithSize = new IngredientWithSize();
        macaroniWithSize.setIngredient(ingredientsService.getIngredient("Raw macaroni"));
        macaroniWithSize.setCount(300);
        macaroniWithSize.setSizeType(IngredientSizeType.G);

        IngredientWithSize saltWithSize = new IngredientWithSize();
        saltWithSize.setIngredient(ingredientsService.getIngredient("Salt"));
        saltWithSize.setCount(150);
        saltWithSize.setSizeType(IngredientSizeType.G);

        receipt1.setKitchenEquipment(ingredientsService.getKitchenItems().stream().map(x -> ingredientsService.wrapIngredientWithSize(x, 100, IngredientSizeType.X)).collect(Collectors.toList()));
        receipt2.setKitchenEquipment(ingredientsService.getKitchenItems().stream().map(x -> ingredientsService.wrapIngredientWithSize(x, 100, IngredientSizeType.X)).collect(Collectors.toList()));

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

        List<Step> stepsForReceipt1 = new ArrayList<>(List.of(step1, step2, step3, lastStep(step3.getStepNumber() + 1)));

        receipt1.setSteps(stepsForReceipt1);
        receipt1.setIngredients(ingredientsService.sumOfIngredientsWithSize(stepsForReceipt1));
        receipt1.setTimeToPrepare(Duration.ofMinutes(30));

        IngredientWithSize ingredientWithSize1 = new IngredientWithSize();
        ingredientWithSize1.setIngredient(ingredientsService.getIngredient("Ketchup"));
        ingredientWithSize1.setSizeType(IngredientSizeType.X);
        ingredientWithSize1.setCount(100);

        IngredientWithSize ingredientWithSize2 = new IngredientWithSize();
        ingredientWithSize2.setIngredient(ingredientsService.getIngredient("Pizza"));
        ingredientWithSize2.setSizeType(IngredientSizeType.X);
        ingredientWithSize2.setCount(200);

        Step step1ForReceipt2 = new Step();
        step1ForReceipt2.setStepNumber(1);
        step1ForReceipt2.setIngredientsUsed(new ArrayList<>(List.of(ingredientWithSize1)));
        step1ForReceipt2.setDescription("Stavi voda vo ringla");
        step1ForReceipt2.setPictureUrl("https://www.thoughtco.com/thmb/r213S4ELSf1XozTXmITBspeLz6Y=/1333x1000/smart/filters:no_upscale()/BoilingWater-58e3d1ac5f9b58ef7e060f93.jpg");
        step1ForReceipt2.setDuration(Duration.ofMinutes(15));

        Step step2ForReceipt2 = new Step();
        step2ForReceipt2.setStepNumber(2);
        step2ForReceipt2.setIngredientsUsed(new ArrayList<>(List.of(saltWithSize)));
        step2ForReceipt2.setDescription("Stavi sol vo tafceto");
        step2ForReceipt2.setPictureUrl("https://media.gettyimages.com/photos/salt-being-sprinkled-into-pot-of-water-picture-id162758559?s=2048x2048");
        step2ForReceipt2.setDuration(Duration.ofSeconds(5));

        Step step3ForReceipt2 = new Step();
        step3ForReceipt2.setStepNumber(3);
        step3ForReceipt2.setIngredientsUsed(new ArrayList<>());
        step3ForReceipt2.setDescription("Iskluci ja ringlata");
        step3ForReceipt2.setPictureUrl("https://thumbs.dreamstime.com/z/turn-oven-dial-wit-hnd-hand-close-heat-meat-lunch-dinner-food-preparation-ingredient-cooking-chef-house-family-ready-gourmet-139076178.jpg");
        step3ForReceipt2.setDuration(Duration.ZERO);

        List<Step> stepsForReceipt2 = new ArrayList<>(List.of(step1ForReceipt2, step2ForReceipt2, step3ForReceipt2, lastStep(step3ForReceipt2.getStepNumber() + 1)));

        receipt2.setSteps(stepsForReceipt2);
        receipt2.setIngredients(ingredientsService.sumOfIngredientsWithSize(stepsForReceipt2));
        receipt2.setTimeToPrepare(Duration.ofMinutes(30));

        return Stream.of(receipt1, receipt2).collect(Collectors.toList());
    }

    private Step lastStep(int stepNumber) {
        Step lastStep = new Step();
        lastStep.setStepNumber(stepNumber);
        lastStep.setIngredientsUsed(new ArrayList<>());
        lastStep.setDuration(Duration.ZERO);
        lastStep.setPictureUrl("https://pbs.twimg.com/media/FKxX4qfX0AEGb-B.jpg");
        lastStep.setDescription("Congratulations on finishing this receipt");

        return lastStep;
    }

    private List<Receipt> shuffleRecipes(List<Receipt> recipes) {
        recipes = recipes.stream()
                .flatMap(receipt -> Collections.nCopies(5, receipt).stream())
                .collect(Collectors.toList());

        Collections.shuffle(recipes);

        return recipes;
    }

    private ReceiptDto calcReceiptForUser(List<IngredientWithSize> ingredientsOfUser, Receipt receipt) {

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setReceipt(receipt);
        receiptDto.setCanMake(false);

        List<IngredientWithSize> receiptIngredients = receipt.getIngredients();

        for (IngredientWithSize ingredientWithSize : receiptIngredients) {
            boolean contains = ingredientsOfUser
                    .stream()
                    .anyMatch(x -> x.getIngredient()
                            .equals(ingredientWithSize.getIngredient())
                    );

            if (!contains) return receiptDto;
        }

        receiptDto.setCanMake(true);

        return receiptDto;
    }
}
