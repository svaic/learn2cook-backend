package com.ukim.finki.learn2cookbackend.service;

import com.ukim.finki.learn2cookbackend.exception.UserNotFoundException;
import com.ukim.finki.learn2cookbackend.model.*;
import com.ukim.finki.learn2cookbackend.model.enumerable.IngredientType;
import com.ukim.finki.learn2cookbackend.model.enumerable.UserType;
import com.ukim.finki.learn2cookbackend.model.interfaces.ListOperation;
import com.ukim.finki.learn2cookbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public User findUser(String username) {
        Optional<User> user = userRepository.findById(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get();
    }

    public User getDefaultUserSettings() {
        // default data
        User user = new User();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setType(UserType.DEFAULT);
        user.setPoints(0);
        user.setKitchenItems(new ArrayList<>());
        user.setFridgeItems(new ArrayList<>());
        user.setReceiptsDone(new ArrayList<>());

        Settings settings = new Settings();
        settings.setFilterIngredients(new ArrayList<>());
        settings.setVegan(false);
        settings.setMaxCalories(null);

        LocalTime breakfastFrom = LocalTime.of(9, 0);
        LocalTime breakfastTo = LocalTime.of(11, 0);

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
        user.setPassword(passwordEncoder.encode(password));
        return saveUser(user);
    }

    public User changeIngredients(User user, IngredientWithSize ingredientToChange, boolean isAddIngredient) {
        ListOperation add = List::add;
        ListOperation remove = List::remove;

        ListOperation listOperation = isAddIngredient ? add : remove;

        List<IngredientWithSize> filteredIngredients =
                ingredientToChange.getIngredient().getType() == IngredientType.FRIDGE ?
                        user.getFridgeItems() : user.getKitchenItems();

        listOperation.apply(filteredIngredients, ingredientToChange);

        return saveUser(user);
    }

    public User addReceiptDone(String username, Long receiptId, MultipartFile image) {
        User user = findUser(username);

        ReceiptDone receiptDone = certificateService.createReceiptDone(receiptId, image);

        Optional<ReceiptDone> receiptAlreadyInList =
                user.getReceiptsDone()
                .stream()
                .filter(x->x.getReceipt().getId().equals(receiptId))
                .findAny();

        if (receiptAlreadyInList.isPresent()) {
            receiptAlreadyInList.get().setImageUrl(receiptDone.getImageUrl());
        } else {
            user.getReceiptsDone().add(receiptDone);
        }

        user.setPoints(calculatePoints(user));

        return saveUser(user);
    }

    private Integer calculatePoints(User user) {
        return user.getReceiptsDone()
                .stream()
                .mapToInt(x->x.getReceipt().getPoints())
                .sum();
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
