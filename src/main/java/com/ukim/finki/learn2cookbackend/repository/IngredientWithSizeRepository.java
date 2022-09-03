package com.ukim.finki.learn2cookbackend.repository;

import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientWithSizeRepository extends JpaRepository<IngredientWithSize, Long> {
}
