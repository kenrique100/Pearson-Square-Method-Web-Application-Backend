package com.feedformulation.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for ingredient details used in feed formulation.
 * Represents a specific ingredient with name, crude protein content, and quantity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient2DTO {

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
