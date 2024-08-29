package com.api.feedFormulation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for feed response information.
 * This class represents the response returned after a feed formulation
 * calculation and includes all necessary details about the formulation.
 */
@Data
@Builder
public class FeedResponseDTO {

    /**
     * Unique identifier for the feed formulation.
     * This ID is generated and used to uniquely identify a specific formulation.
     */
    private String formulationId;

    /**
     * The date when the feed formulation was created.
     * Stored in a string format, typically as yyyy-MM-dd.
     */
    private String date;

    /**
     * The total quantity of feed formulation.
     * Represents the amount of feed in kilograms.
     */
    private double quantity;

    /**
     * The target crude protein (CP) value for the feed formulation.
     * Represents the desired protein content percentage.
     */
    private double targetCpValue;

    /**
     * List of ingredients used in the feed formulation.
     * Each ingredient includes details such as name, crude protein content,
     * and quantity used in the formulation.
     */
    private List<IngredientDTO> ingredients;
}
