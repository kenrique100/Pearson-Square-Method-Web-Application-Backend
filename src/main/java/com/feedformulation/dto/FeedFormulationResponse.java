package com.feedformulation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FeedFormulationResponse {

    // Unique identifier for the feed formulation
    private String formulationId;

    // Name of the feed formulation
    private String formulationName;

    // Date when the formulation was created
    private LocalDate date;

    // Total quantity of the feed formulation in kilograms
    private double totalQuantityKg;

    // Target crude protein (CP) value for the formulation
    private double targetCpValue;

    // List of ingredients used in the feed formulation
    private List<Ingredient2DTO> ingredient2s;
}
