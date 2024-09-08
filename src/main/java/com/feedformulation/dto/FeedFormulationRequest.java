package com.feedformulation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FeedFormulationRequest {

    @NotNull(message = "Formulation name is required.")
    private String formulationName; // Name of the custom feed formulation

    @NotNull
    private IngredientsRequest ingredients; // Contains a list of ingredient categories (proteins, carbohydrates)

    /**
     * Nested static class to represent the structure of the ingredients in a feed formulation.
     * Each category (proteins, carbohydrates) will contain a list of CustomIngredientDTO objects.
     */
    @Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods for the nested class
    public static class IngredientsRequest {

        @NotNull
        private List<Ingredient2DTO> proteins; // List of protein ingredients in the formulation

        @NotNull
        private List<Ingredient2DTO> carbohydrates; // List of carbohydrate ingredients in the formulation
    }
}
