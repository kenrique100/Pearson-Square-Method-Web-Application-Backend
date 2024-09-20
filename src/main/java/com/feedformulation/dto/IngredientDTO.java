package com.feedformulation.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for ingredient details used in feed formulation.
 * Represents a specific ingredient with name, crude protein content, and quantity.
 */
@Data
@Builder
public class IngredientDTO {

    /**
     * Name of the ingredient.
     */
    private String name;

    /**
     * Crude protein content of the ingredient in percentage.
     */
    private double crudeProtein;

    /**
     * Quantity of the ingredient used in the feed formulation (kg).
     */
    private double quantity;
}
