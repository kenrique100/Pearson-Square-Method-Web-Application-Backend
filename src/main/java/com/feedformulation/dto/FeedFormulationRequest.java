package com.feedformulation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FeedFormulationRequest {

    @NotNull(message = "Formulation name is required.")
    private String formulationName;

    @NotEmpty(message = "At least one protein ingredient is required.")
    private List<IngredientRequest> proteins;  // List for protein ingredients

    @NotEmpty(message = "At least one carbohydrate ingredient is required.")
    private List<IngredientRequest> carbohydrates;  // List for carbohydrate ingredients

    @Data
    public static class IngredientRequest {
        @NotNull
        private String name;
        @NotNull
        private double quantityKg;
    }
}
