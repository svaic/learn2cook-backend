package com.ukim.finki.learn2cookbackend.service;

import com.ukim.finki.learn2cookbackend.exception.UserNotFound;
import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.Settings;
import com.ukim.finki.learn2cookbackend.model.MealPeriod;
import com.ukim.finki.learn2cookbackend.model.User;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientSizeType;
import com.ukim.finki.learn2cookbackend.model.enumerable.UserType;
import com.ukim.finki.learn2cookbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private UserRepository userRepository;

    public User findUser(String username) {
        Optional<User> user = userRepository.findById(username);

        if (user.isEmpty()) {
            throw new UserNotFound();
        }

        return user.get();
    }

    public User getDefaultUserSettings() {
        // default data
        User user = new User();
        user.setType(UserType.DEFAULT);
        user.setPoints(0);
        user.setKitchenItems(ingredientsService.getMockKitchenItems());

        List<IngredientWithSize> fridgeIngredients =
                ingredientsService.getMockIngredients()
                        .stream()
                        .map(x->ingredientsService.wrapIngredientWithSize(x, 500, IngredientSizeType.KG))
                        .collect(Collectors.toList());

        user.setFridgeItems(fridgeIngredients);

        Settings settings = new Settings();
        settings.setFilterIngredients(new ArrayList<>());
        settings.setVegan(false);
        settings.setMaxCalories(null);

        LocalTime breakfastFrom = LocalTime.of(9,0);
        LocalTime breakfastTo = LocalTime.of(11,0);

        MealPeriod breakFast = periodFactory(breakfastFrom, breakfastTo, false, true);

        LocalTime lunchFrom = LocalTime.of(14,0);
        LocalTime lunchTo = LocalTime.of(16,0);

        MealPeriod lunch = periodFactory(lunchFrom, lunchTo, true, true);

        LocalTime dinnerFrom = LocalTime.of(20,0);
        LocalTime dinnerTo = LocalTime.of(22,0);

        MealPeriod dinner = periodFactory(dinnerFrom, dinnerTo, true, false);

        settings.setBreakfast(breakFast);
        settings.setLunch(lunch);
        settings.setDinner(dinner);

        user.setSettings(settings);
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User registerUser(String username, String password) {
        User user = getDefaultUserSettings();
        user.setUsername(username);
        user.setPassword(password);
        return saveUser(user);
    }

    private MealPeriod periodFactory(LocalTime from, LocalTime to, boolean salad, boolean desert) {
        MealPeriod mealPeriod = new MealPeriod();

        mealPeriod.setFromTime(from);
        mealPeriod.setToTime(to);
        mealPeriod.setSalad(salad);
        mealPeriod.setDesert(desert);

        return mealPeriod;
    }
}
