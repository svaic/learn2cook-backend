package com.ukim.finki.learn2cookbackend.repository;

import com.ukim.finki.learn2cookbackend.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
