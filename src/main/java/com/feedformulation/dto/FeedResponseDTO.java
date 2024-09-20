package com.feedformulation.dto;

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
     */
    private String formulationId;

    /**
     * The name of the feed formulation.
     * This name is descriptive and not used as a unique identifier.
     */
    private String formulationName;

    /**
     * The date when the feed formulation was created.
     * Stored in string format, typically as yyyy-MM-dd.
     */
    private String date;

    /**
     * The total quantity of feed formulation in kilograms.
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
