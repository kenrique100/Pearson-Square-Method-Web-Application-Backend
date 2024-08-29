package com.api.feedFormulation.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for ingredient details used in feed formulation.
 * This class represents the information about a specific ingredient, including
 * its name, crude protein content, and quantity.
 */
@Data
@Builder
public class IngredientDTO {

    /**
     * Name of the ingredient.
     * Describes the type of ingredient used in the feed formulation.
     */
    private String name;

    /**
     * Crude protein content of the ingredient.
     * Represents the percentage of protein in the ingredient.
     */
    private double crudeProtein;

    /**
     * Quantity of the ingredient used in the feed formulation.
     * Represents the amount of the ingredient in kilograms.
     */
    private double quantity;

    /**
     * Constructor to initialize the fields of the IngredientDTO.
     *
     * @param name The name of the ingredient.
     * @param crudeProtein The crude protein content of the ingredient.
     * @param quantity The quantity of the ingredient used.
     */
    public IngredientDTO(String name, double crudeProtein, double quantity) {
        this.name = name;
        this.crudeProtein = crudeProtein;
        this.quantity = quantity;
    }
}
