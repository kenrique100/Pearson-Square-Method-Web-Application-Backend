package com.feedformulation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor // Add this for no-argument constructor
public class FeedFormulationRequest {

    @NotNull(message = "Formulation name is required.")
    private String formulationName;

    @NotEmpty(message = "At least one protein ingredient is required.")
    private List<IngredientRequest> proteins;

    @NotEmpty(message = "At least one carbohydrate ingredient is required.")
    private List<IngredientRequest> carbohydrates;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IngredientRequest {
        @NotNull
        private String name;

        @NotNull
        private double quantityKg;
    }
}
