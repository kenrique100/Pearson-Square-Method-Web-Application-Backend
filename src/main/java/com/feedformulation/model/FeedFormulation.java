package com.feedformulation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedFormulation {

    @Id
    @Column(length = 5, unique = true, nullable = false)
    private String formulationId;

    @Column(unique = true, nullable = false)
    private String formulationName;

    @Builder.Default
    @Column(nullable = false)
    private LocalDate date = LocalDate.now(); // Default value for date

    @Column(name = "total_quantity_kg")
    private double totalQuantityKg;

    @Column(nullable = false)
    private double targetCpValue;

    // Set FetchType to EAGER so ingredients are fetched when FeedFormulation is queried
    @Builder.Default
    @OneToMany(mappedBy = "feedFormulation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // Prevents cyclic reference issue
    private List<Ingredient2> ingredient2s = new ArrayList<>(); // Default empty list for ingredients

    // Method to add ingredient to formulation and set the relationship
    public void addIngredient(Ingredient2 ingredient2) {
        ingredient2.setFeedFormulation(this);
        this.ingredient2s.add(ingredient2);
    }
}
