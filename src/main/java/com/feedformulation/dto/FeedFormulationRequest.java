package com.feedformulation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class FeedFormulationRequest {

    // The name of the feed formulation, which is required
    @NotNull(message = "Formulation name is required.")
    private String formulationName;

    // List of protein ingredients, must contain at least one ingredient
    @NotEmpty(message = "At least one protein ingredient is required.")
    private List<IngredientRequest> proteins;

    // List of carbohydrate ingredients, must contain at least one ingredient
    @NotEmpty(message = "At least one carbohydrate ingredient is required.")
    private List<IngredientRequest> carbohydrates;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IngredientRequest {

        // The name of the ingredient, which is required
        @NotNull
        private String name;

        // The quantity of the ingredient in kilograms, must be a positive value
        @Positive(message = "Quantity must be positive.")
        private double quantityKg;
    }
}
