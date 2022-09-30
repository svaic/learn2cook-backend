package com.ukim.finki.learn2cookbackend.service;

import com.ukim.finki.learn2cookbackend.exception.ReceiptNotFoundException;
import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.Receipt;
import com.ukim.finki.learn2cookbackend.model.Step;
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

    public List<Receipt> getRecipes() {
        return receiptRepository.findAll();
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
        receipt1.setType(ReceiptType.LUNCH);
        receipt1.setCalories(2000);
        receipt1.setDifficulty(ReceiptDifficulty.MEDIUM);
        receipt1.setPictureUrl("https://www.ruchiskitchen.com/wp-content/uploads/2022/03/Indian-style-Vegetable-Macaroni-3-500x500.jpg");
        receipt1.setPoints(50);

        IngredientWithSize macaroniWithSize = new IngredientWithSize();
        macaroniWithSize.setIngredient(ingredientsService.getIngredient("макарони"));
        macaroniWithSize.setCount(300);
        macaroniWithSize.setSizeType(IngredientSizeType.G);

        IngredientWithSize saltWithSize = new IngredientWithSize();
        saltWithSize.setIngredient(ingredientsService.getIngredient("сол"));
        saltWithSize.setCount(150);
        saltWithSize.setSizeType(IngredientSizeType.G);

        receipt1.setKitchenEquipment(ingredientsService.getKitchenItems().stream().map(x -> ingredientsService.wrapIngredientWithSize(x, 100, IngredientSizeType.X)).collect(Collectors.toList()));

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

        Receipt receipt2 = new Receipt();
        receipt2.setName("Pizza");
        receipt2.setCategory(FoodCategory.MAIN);
        receipt2.setType(ReceiptType.LUNCH);
        receipt2.setCalories(2400);
        receipt2.setDifficulty(ReceiptDifficulty.HARD);
        receipt2.setPictureUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg");
        receipt2.setPoints(80);

        receipt2.setKitchenEquipment(ingredientsService.getKitchenItems().stream().map(x -> ingredientsService.wrapIngredientWithSize(x, 100, IngredientSizeType.X)).collect(Collectors.toList()));

        IngredientWithSize ingredientWithSize1 = new IngredientWithSize();
        ingredientWithSize1.setIngredient(ingredientsService.getIngredient("кечап"));
        ingredientWithSize1.setSizeType(IngredientSizeType.X);
        ingredientWithSize1.setCount(100);

        IngredientWithSize ingredientWithSize2 = new IngredientWithSize();
        ingredientWithSize2.setIngredient(ingredientsService.getIngredient("пица"));
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

        return Stream.of(receipt1, receipt2, getPancakeReceipt(), getEggs(), getSteak(), getCornflakes()).collect(Collectors.toList());
    }

    private Receipt getPancakeReceipt() {
        Receipt receipt = new Receipt();
        receipt.setName("Палачинки");
        receipt.setCategory(FoodCategory.DESERT);
        receipt.setType(ReceiptType.DINNER);
        receipt.setCalories(61);
        receipt.setDifficulty(ReceiptDifficulty.MEDIUM);
        receipt.setPictureUrl("https://images.immediate.co.uk/production/volatile/sites/30/2020/08/recipe-image-legacy-id-1273477_8-ad36e3b.jpg?resize=960,872?quality=90&webp=true&resize=300,272");
        receipt.setPoints(60);

        IngredientWithSize flour = new IngredientWithSize();
        flour.setIngredient(ingredientsService.getIngredient("брашно"));
        flour.setSizeType(IngredientSizeType.G);
        flour.setCount(100);

        IngredientWithSize eggs = new IngredientWithSize();
        eggs.setIngredient(ingredientsService.getIngredient("јајце"));
        eggs.setSizeType(IngredientSizeType.X);
        eggs.setCount(2);

        IngredientWithSize milk = new IngredientWithSize();
        milk.setIngredient(ingredientsService.getIngredient("млеко"));
        milk.setSizeType(IngredientSizeType.G);
        milk.setCount(300);

        IngredientWithSize oil = new IngredientWithSize();
        oil.setIngredient(ingredientsService.getIngredient("масло за готвење"));
        oil.setSizeType(IngredientSizeType.X);
        oil.setCount(1);

        IngredientWithSize pot = new IngredientWithSize();
        pot.setIngredient(ingredientsService.getIngredient("тава за пржење"));
        pot.setSizeType(IngredientSizeType.X);
        pot.setCount(1);

        IngredientWithSize jug = new IngredientWithSize();
        jug.setIngredient(ingredientsService.getIngredient("сад"));
        jug.getIngredient().setPictureUrl("https://static.vecteezy.com/system/resources/previews/003/426/879/original/sticker-pitcher-of-water-on-white-background-free-vector.jpg");
        jug.setSizeType(IngredientSizeType.X);
        jug.setCount(1);

        Step step1 = new Step();
        step1.setStepNumber(1);
        step1.setIngredientsUsed(new ArrayList<>(List.of(flour, pot, milk, oil, jug )));
        step1.setDescription("Put 100g plain flour, 2 large eggs, 300ml milk, 1 tbsp sunflower or vegetable oil and a pinch of salt into a bowl or large jug, then whisk to a smooth batter.");
        step1.setPictureUrl("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/how-to-make-self-raising-flour-1587567240.jpg");
        step1.setDuration(Duration.ofMinutes(1));

        Step step2 = new Step();
        step2.setStepNumber(2);
        step2.setIngredientsUsed(new ArrayList<>());
        step2.setDescription("Set aside for 30 mins to rest if you have time, or start cooking straight away.");
        step2.setPictureUrl("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/how-to-make-self-raising-flour-1587567240.jpg");
        step2.setDuration(Duration.ofMinutes(30));

        Step step3 = new Step();
        step3.setStepNumber(3);
        step3.setIngredientsUsed(new ArrayList<>(List.of(pot, oil)));
        step3.setDescription("Set a medium frying pan or crêpe pan over a medium heat and carefully wipe it with some oiled kitchen paper.");
        step3.setPictureUrl("https://www.wikihow.com/images/thumb/5/53/Boil-Water-Using-a-Kettle-Step-2.jpg/v4-460px-Boil-Water-Using-a-Kettle-Step-2.jpg.webp");
        step3.setDuration(Duration.ofMinutes(5));

        Step step4 = new Step();
        step4.setStepNumber(4);
        step4.setIngredientsUsed(new ArrayList<>(List.of(pot, oil)));
        step4.setDescription("When hot, cook your pancakes for 1 min on each side until golden, keeping them warm in a low oven as you go.");
        step4.setPictureUrl("https://upload.wikimedia.org/wikipedia/commons/e/ec/Pancake_in_frying_pan.jpg");
        step4.setDuration(Duration.ofMinutes(10));

        List<Step> steps = new ArrayList<>(List.of(step1, step2, step3, step4, lastStep(step4.getStepNumber() + 1)));

        receipt.setSteps(steps);
        receipt.setIngredients(ingredientsService.sumOfIngredientsWithSize(steps));
        receipt.setTimeToPrepare(Duration.ofMinutes(45));

        return receipt;
    }

    private Receipt getEggs() {
        Receipt receipt = new Receipt();
        receipt.setName("Кајгана");
        receipt.setCategory(FoodCategory.MAIN);
        receipt.setType(ReceiptType.BREAKFAST);
        receipt.setCalories(148);
        receipt.setDifficulty(ReceiptDifficulty.EASY);
        receipt.setPictureUrl("https://www.loveandlemons.com/wp-content/uploads/2021/05/scrambled-eggs.jpg");
        receipt.setPoints(30);

        IngredientWithSize eggs = new IngredientWithSize();
        eggs.setIngredient(ingredientsService.getIngredient("јајце"));
        eggs.setSizeType(IngredientSizeType.X);
        eggs.setCount(4);

        IngredientWithSize milk = new IngredientWithSize();
        milk.setIngredient(ingredientsService.getIngredient("млеко"));
        milk.setSizeType(IngredientSizeType.G);
        milk.setCount(300);

        IngredientWithSize oil = new IngredientWithSize();
        oil.setIngredient(ingredientsService.getIngredient("масло за готвење"));
        oil.setSizeType(IngredientSizeType.X);
        oil.setCount(1);

        IngredientWithSize whisk = new IngredientWithSize();
        whisk.setIngredient(ingredientsService.getIngredient("мешало"));
        whisk.setSizeType(IngredientSizeType.X);
        whisk.setCount(1);

        IngredientWithSize jug = new IngredientWithSize();
        jug.setIngredient(ingredientsService.getIngredient("сад"));
        jug.setSizeType(IngredientSizeType.X);
        jug.setCount(1);

        IngredientWithSize pan = new IngredientWithSize();
        pan.setIngredient(ingredientsService.getIngredient("тава за пржење"));
        pan.setSizeType(IngredientSizeType.X);
        pan.setCount(1);

        IngredientWithSize oven = new IngredientWithSize();
        oven.setIngredient(ingredientsService.getIngredient("шпорет"));
        oven.setSizeType(IngredientSizeType.X);
        oven.setCount(1);

        Step step1 = new Step();
        step1.setStepNumber(1);
        step1.setIngredientsUsed(new ArrayList<>(List.of(eggs, jug)));
        step1.setDescription("Искрши ги јајцата и стави ја жолчката во сад за готвење");
        step1.setPictureUrl("https://s3.theasianparent.com/tap-assets-prod/wp-content/uploads/sites/31/2018/10/how-to-crack-an-egg-feat-1-768x384.jpg");
        step1.setDuration(Duration.ofMinutes(5));

        Step step2 = new Step();
        step2.setStepNumber(2);
        step2.setIngredientsUsed(new ArrayList<>(List.of(eggs, whisk)));
        step2.setDescription("Измешај ги јајцата во садот со мешалото");
        step2.setPictureUrl("https://www.loveandlemons.com/wp-content/uploads/2021/05/how-to-scramble-eggs.jpg");
        step2.setDuration(Duration.ofMinutes(4));

        Step step3 = new Step();
        step3.setStepNumber(3);
        step3.setIngredientsUsed(new ArrayList<>(List.of(pan, oven, oil)));
        step3.setDescription("стави го тавчето на рингла, и укличи ја ринглата, стави масло во тавчето");
        step3.setPictureUrl("https://www.telegraph.co.uk/content/dam/food-and-drink/2018/06/28/Solidteknics_trans_NvBQzQNjv4BqdBpDpX3Rmty5DzuhbRUfmhKi2sT3vi7ux2-RDZwC4QA.JPG?imwidth=480");
        step3.setDuration(Duration.ofMinutes(3));

        Step step4 = new Step();
        step4.setStepNumber(4);
        step4.setIngredientsUsed(new ArrayList<>(List.of(eggs, pan)));
        step4.setDescription("стави ги јајцата во тавчето, и превртувај ги кога ќе обелат");
        step4.setPictureUrl("https://media.eggs.ca/assets/Eggs101/_resampled/FillWyIxMjgwIiwiNzIwIl0/0L1C0018.jpg");
        step4.setDuration(Duration.ofMinutes(5));

        List<Step> steps = new ArrayList<>(List.of(step1, step2, step3, step4, lastStep(step4.getStepNumber() + 1)));

        receipt.setSteps(steps);
        receipt.setIngredients(ingredientsService.sumOfIngredientsWithSize(steps));
        receipt.setTimeToPrepare(Duration.ofMinutes(20));

        return receipt;
    }

    private Receipt getSteak() {
        Receipt receipt = new Receipt();
        receipt.setName("Пилешко месо");
        receipt.setCategory(FoodCategory.MAIN);
        receipt.setType(ReceiptType.DINNER);
        receipt.setCalories(271);
        receipt.setDifficulty(ReceiptDifficulty.HARD);
        receipt.setPictureUrl("https://tastycraze.com/files/lib/400x296/roast-chicken-steaks.jpg");
        receipt.setPoints(150);

        IngredientWithSize grill = new IngredientWithSize();
        grill.setIngredient(ingredientsService.getIngredient("скара"));
        grill.setSizeType(IngredientSizeType.X);
        grill.setCount(1);

        IngredientWithSize steak = new IngredientWithSize();
        steak.setIngredient(ingredientsService.getIngredient("стек"));
        steak.setSizeType(IngredientSizeType.G);
        steak.setCount(400);

        IngredientWithSize potatoes = new IngredientWithSize();
        potatoes.setIngredient(ingredientsService.getIngredient("компир"));
        potatoes.setSizeType(IngredientSizeType.G);
        potatoes.setCount(500);

        Step step1 = new Step();
        step1.setStepNumber(1);
        step1.setIngredientsUsed(new ArrayList<>(List.of(grill)));
        step1.setDescription("запали ја скарата");
        step1.setPictureUrl("https://cdn.apartmenttherapy.info/image/upload/v1590169597/k/Photo/Lifestyle/2020-05-HT-Clean-Grill-Grates/HT-Clean-Grill-Grates-01.jpg");
        step1.setDuration(Duration.ofMinutes(5));

        Step step2 = new Step();
        step2.setStepNumber(2);
        step2.setIngredientsUsed(new ArrayList<>(List.of(steak, grill)));
        step2.setDescription("стави ги стековите на скарата и на секој 5 минути врти се дур не станат како на слиакта");
        step2.setPictureUrl("https://www.lecker.de/assets/styles/563x422/public/haehnchenbrust-grillen_0.jpg?itok=Nrr2ofMi");
        step2.setDuration(Duration.ofMinutes(20));

        Step step3 = new Step();
        step3.setStepNumber(3);
        step3.setIngredientsUsed(new ArrayList<>(List.of(potatoes)));
        step3.setDescription("излупи ги компирите");
        step3.setPictureUrl("https://www.firstforwomen.com/wp-content/uploads/sites/2/2020/11/how-to-peel-potatoes.jpg");
        step3.setDuration(Duration.ofMinutes(10));

        Step step4 = new Step();
        step4.setStepNumber(4);
        step4.setIngredientsUsed(new ArrayList<>(List.of(potatoes, grill)));
        step4.setDescription("стави ги на скара компирите");
        step4.setPictureUrl("https://images-gmi-pmc.edge-generalmills.com/41a23e8c-3704-4719-bc61-d22d69f9ef29.jpg");
        step4.setDuration(Duration.ofMinutes(15));

        List<Step> steps = new ArrayList<>(List.of(step1, step2, step3, step4, lastStep(step4.getStepNumber() + 1)));

        receipt.setSteps(steps);
        receipt.setIngredients(ingredientsService.sumOfIngredientsWithSize(steps));
        receipt.setTimeToPrepare(Duration.ofMinutes(50));

        return receipt;
    }

    private Receipt getCornflakes() {
        Receipt receipt = new Receipt();
        receipt.setName("Житари");
        receipt.setCategory(FoodCategory.MAIN);
        receipt.setType(ReceiptType.BREAKFAST);
        receipt.setCalories(357);
        receipt.setDifficulty(ReceiptDifficulty.EASY);
        receipt.setPictureUrl("https://thumbs.dreamstime.com/b/bowl-cornflakes-filled-milk-171077019.jpg");
        receipt.setPoints(10);

        IngredientWithSize cornflakes = new IngredientWithSize();
        cornflakes.setIngredient(ingredientsService.getIngredient("житари"));
        cornflakes.setSizeType(IngredientSizeType.G);
        cornflakes.setCount(300);

        IngredientWithSize milk = new IngredientWithSize();
        milk.setIngredient(ingredientsService.getIngredient("млеко"));
        milk.setSizeType(IngredientSizeType.G);
        milk.setCount(300);

        Step step1 = new Step();
        step1.setStepNumber(1);
        step1.setIngredientsUsed(new ArrayList<>(List.of(cornflakes)));
        step1.setDescription("земи празен тањир и стави житари во него");
        step1.setPictureUrl("https://media.istockphoto.com/photos/filling-bowl-with-cereal-picture-id174901003?s=612x612");
        step1.setDuration(Duration.ofMinutes(1));

        Step step2 = new Step();
        step2.setStepNumber(2);
        step2.setIngredientsUsed(new ArrayList<>(List.of(milk)));
        step2.setDescription("напуни го тањирот со млеко");
        step2.setPictureUrl("https://previews.123rf.com/images/tverdohlib/tverdohlib1506/tverdohlib150600003/40554342-sweet-krispy-of-cereal-cornflakes-with-frosting-in-light-green-plate-and-spoon-filling-in-with-milk-.jpg");
        step2.setDuration(Duration.ofMinutes(1));

        List<Step> steps = new ArrayList<>(List.of(step1, step2, lastStep(step2.getStepNumber() + 1)));

        receipt.setSteps(steps);
        receipt.setIngredients(ingredientsService.sumOfIngredientsWithSize(steps));
        receipt.setTimeToPrepare(Duration.ofMinutes(3));

        return receipt;

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
