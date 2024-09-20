package com.feedformulation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FeedFormulationResponse {
    private String formulationId;
    private String formulationName;
    private LocalDate date;
    private double totalQuantityKg;
    private double targetCpValue;
    private List<Ingredient2DTO> ingredient2s;
}
