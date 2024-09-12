package com.feedformulation.service;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.dto.FeedFormulationResponse;
import com.feedformulation.dto.Ingredient2DTO;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.model.Ingredient2;
import com.feedformulation.repository.FeedFormulationRepository2;
import com.feedformulation.utils.FeedFormulationSupport2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedFormulationService2Impl implements FeedFormulationService2 {

    private final FeedFormulationRepository2 feedFormulationRepository2;
    private final FeedFormulationSupport2 feedFormulationSupport2;

    @Override
    public FeedFormulation createFeedFormulation(FeedFormulationRequest request) {
        FeedFormulation formulation = FeedFormulation.builder()
                .formulationId(generateGuid())
                .formulationName(request.getFormulationName())
                .date(LocalDate.now()) // Set current date during creation
                .totalQuantityKg(feedFormulationSupport2.calculateTotalQuantity(request))
                .targetCpValue(feedFormulationSupport2.calculateTargetCpValue(request))
                .build();

        // Create and associate ingredients with the formulation
        List<Ingredient2> ingredient2s = feedFormulationSupport2.createIngredients(request, formulation);
        formulation.setIngredient2s(ingredient2s);

        return feedFormulationRepository2.save(formulation);
    }

    @Override
    public FeedFormulation getFeedFormulationByIdAndDate(String id, String date) {
        LocalDate localDate = LocalDate.parse(date);
        return feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));
    }

    @Override
    public List<FeedFormulationResponse> getAllFeedFormulations() {
        List<FeedFormulation> formulations = feedFormulationRepository2.findAll();
        return formulations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FeedFormulation updateFeedFormulationByIdAndDate(String id, String date, FeedFormulationRequest request) {
        LocalDate localDate = LocalDate.parse(date);
        FeedFormulation existing = feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));

        // Update simple fields
        existing.setFormulationName(request.getFormulationName());
        existing.setTargetCpValue(feedFormulationSupport2.calculateTargetCpValue(request));
        existing.setTotalQuantityKg(feedFormulationSupport2.calculateTotalQuantity(request));

        // Update ingredients collection
        List<Ingredient2> newIngredient2s = feedFormulationSupport2.createIngredients(request, existing);

        // Clear the existing collection to avoid orphaning issues
        existing.getIngredient2s().clear();
        existing.getIngredient2s().addAll(newIngredient2s);

        // Save the updated feed formulation
        return feedFormulationRepository2.save(existing);
    }

    @Override
    public void deleteFeedFormulationByIdAndDate(String id, String date) {
        LocalDate localDate = LocalDate.parse(date);
        FeedFormulation existing = feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));

        feedFormulationRepository2.delete(existing);
    }

    private FeedFormulationResponse mapToResponse(FeedFormulation formulation) {
        return FeedFormulationResponse.builder()
                .formulationId(formulation.getFormulationId())
                .formulationName(formulation.getFormulationName())
                .date(formulation.getDate())
                .totalQuantityKg(formulation.getTotalQuantityKg())
                .targetCpValue(formulation.getTargetCpValue())
                .ingredients(formulation.getIngredient2s().stream()
                        .map(ingredient2 -> Ingredient2DTO.builder()
                                .name(ingredient2.getName()) // Map only the required fields
                                .crudeProtein(ingredient2.getCrudeProtein())
                                .quantityKg(ingredient2.getQuantityKg())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }



    private String generateGuid() {
        return UUID.randomUUID().toString().substring(0, 5);
    }
}
