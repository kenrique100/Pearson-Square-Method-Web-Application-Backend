package com.feedformulation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FeedFormulationRequest {

    // Field to capture the name of the feed formulation, cannot be null
    @NotNull(message = "Formulation name is required.")
    private String formulationName;

    // List of protein ingredients required in the formulation, cannot be empty
    @NotEmpty(message = "At least one protein ingredient is required.")
    private List<IngredientRequest> proteins;  // List for protein ingredients

    // List of carbohydrate ingredients required in the formulation, cannot be empty
    @NotEmpty(message = "At least one carbohydrate ingredient is required.")
    private List<IngredientRequest> carbohydrates;  // List for carbohydrate ingredients

    @Data
    public static class IngredientRequest {
        // Name of the ingredient, cannot be null
        @NotNull
        private String name;

        // Quantity of the ingredient in kilograms, cannot be null
        @NotNull
        private double quantityKg;
    }
}
