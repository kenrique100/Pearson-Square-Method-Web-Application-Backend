package com.feedformulation.service;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.dto.FeedFormulationResponse;
import com.feedformulation.dto.Ingredient2DTO;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.model.Ingredient2;
import com.feedformulation.repository.FeedFormulationRepository2;
import com.feedformulation.utils.FeedFormulationSupport2;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedFormulationService2Impl implements FeedFormulationService2 {

    private final FeedFormulationRepository2 feedFormulationRepository2;
    private final FeedFormulationSupport2 feedFormulationSupport2;

    @Override
    public FeedFormulation createCustomFormulation(FeedFormulationRequest request) {
        validateRequest(request);

        double totalQuantityFromMainIngredients = feedFormulationSupport2.calculateTotalQuantityFromRequest(request);
        double targetCpValueFromMainIngredients = feedFormulationSupport2.calculateTargetCpValue(request);

        FeedFormulation formulation = FeedFormulation.builder()
                .formulationId(generateGuid())
                .formulationName(request.getFormulationName())
                .date(LocalDate.now())
                .totalQuantityKg(totalQuantityFromMainIngredients)
                .targetCpValue(targetCpValueFromMainIngredients)
                .build();

        List<Ingredient2> mainIngredients = feedFormulationSupport2.createIngredients(request, formulation);
        List<Ingredient2> otherIngredients = feedFormulationSupport2.createOtherIngredients(totalQuantityFromMainIngredients, formulation);
        List<Ingredient2> allIngredients = new ArrayList<>(mainIngredients);
        allIngredients.addAll(otherIngredients);

        formulation.setIngredient2s(allIngredients);
        return feedFormulationRepository2.save(formulation);
    }

    @Override
    public FeedFormulation getCustomFormulationByIdAndDate(String id, String date) {
        LocalDate localDate = LocalDate.parse(date);
        return feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));
    }

    @Override
    public List<FeedFormulationResponse> getCustomFormulations() {
        return feedFormulationRepository2.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FeedFormulation updateCustomFeedFormulationByIdAndDate(String id, String date, FeedFormulationRequest request) {
        validateRequest(request);

        LocalDate localDate = LocalDate.parse(date);
        FeedFormulation existing = feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));

        existing.setFormulationName(request.getFormulationName());
        existing.setTargetCpValue(feedFormulationSupport2.calculateTargetCpValue(request));
        existing.setTotalQuantityKg(feedFormulationSupport2.calculateTotalQuantityFromRequest(request));

        List<Ingredient2> newIngredients = feedFormulationSupport2.createIngredients(request, existing);
        existing.getIngredient2s().clear();
        existing.getIngredient2s().addAll(newIngredients);

        return feedFormulationRepository2.save(existing);
    }

    @Override
    public void deleteCustomFeedFormulationByIdAndDate(String id, String date) {
        LocalDate localDate = LocalDate.parse(date);
        FeedFormulation formulation = feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));
        feedFormulationRepository2.delete(formulation);
    }

    private FeedFormulationResponse mapToResponse(FeedFormulation formulation) {
        List<Ingredient2DTO> ingredientDTOs = formulation.getIngredient2s().stream()
                .map(ingredient -> new Ingredient2DTO(
                        ingredient.getName(),
                        ingredient.getQuantityKg(),
                        ingredient.getCrudeProtein()
                ))
                .collect(Collectors.toList());

        return FeedFormulationResponse.builder()
                .formulationId(formulation.getFormulationId())
                .formulationName(formulation.getFormulationName())
                .date(formulation.getDate())
                .targetCpValue(formulation.getTargetCpValue())
                .totalQuantityKg(formulation.getTotalQuantityKg())
                .ingredient2s(ingredientDTOs)
                .build();
    }


    private String generateGuid() {
        return UUID.randomUUID().toString();
    }

    private void validateRequest(FeedFormulationRequest request) {
        if (request.getProteins() == null || request.getProteins().isEmpty()) {
            throw new ValidationException("Proteins are required.");
        }
        if (request.getCarbohydrates() == null || request.getCarbohydrates().isEmpty()) {
            throw new ValidationException("Carbohydrates are required.");
        }
    }
}
