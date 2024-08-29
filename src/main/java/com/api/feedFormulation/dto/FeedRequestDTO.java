package com.api.feedFormulation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for feed request information.
 * This class is used to transfer data from the client to the server
 * for the feed formulation calculation.
 */
@Data
@Builder
public class FeedRequestDTO {

    /**
     * The quantity of the feed formulation requested.
     * Must be between 1 and 1000 kilograms (inclusive).
     */
    @NotNull(message = "Quantity is required.") // Ensures that the quantity is not null
    @Min(value = 1, message = "Quantity must be greater than zero.") // Minimum value constraint
    @Max(value = 1000, message = "Quantity cannot exceed 1000 kg.") // Maximum value constraint
    private double quantity;

    /**
     * The target crude protein (CP) value for the feed formulation.
     * Must be a positive value greater than zero.
     */
    @NotNull(message = "Target CP value is required.") // Ensures that the target CP value is not null
    @Min(value = 0, message = "Target CP value must be greater than zero.") // Minimum value constraint
    private double targetCpValue;

}
