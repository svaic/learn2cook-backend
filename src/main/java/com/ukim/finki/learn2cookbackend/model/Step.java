package com.ukim.finki.learn2cookbackend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    int stepNumber;
    String pictureUrl;
    @ManyToMany
    List<IngredientWithSize> ingredientsUsed;
    String description;
    Duration duration;
}
