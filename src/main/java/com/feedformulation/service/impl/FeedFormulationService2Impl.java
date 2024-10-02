package com.feedformulation.service.impl;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.dto.FeedFormulationResponse;
import com.feedformulation.dto.Ingredient2DTO;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.exception.InvalidInputException;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.model.Ingredient2;
import com.feedformulation.repository.FeedFormulationRepository2;
import com.feedformulation.service.FeedFormulationService2;
import com.feedformulation.utils.FeedFormulationSupport2;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the FeedFormulationService2 interface.
 * This service manages the CRUD operations for feed formulations,
 * including creating, retrieving, updating, and deleting formulations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FeedFormulationService2Impl implements FeedFormulationService2 {

    private final FeedFormulationRepository2 feedFormulationRepository2; // Repository for accessing feed formulations
    private final FeedFormulationSupport2 feedFormulationSupport2; // Utility for feed formulation calculations

    /**
     * Creates a new custom feed formulation based on the provided request.
     *
     * @param request The request object containing formulation details.
     * @return The created FeedFormulation entity.
     */
    @Transactional
    @Override
    public FeedFormulation createCustomFormulation(FeedFormulationRequest request) {
        validateRequest(request); // Validate the incoming request

        // Validate formulation name for valid and invalid formulation name
        String formulationName = request.getFormulationName();
        boolean isFormulationNameInvalid = formulationName == null || formulationName.trim().isEmpty() || feedFormulationRepository2.existsByFormulationName(request.getFormulationName());

        if (isFormulationNameInvalid) {
            throw new InvalidInputException(formulationName == null || formulationName.trim().isEmpty()
                    ? "Formulation name cannot be empty."
                    : "Formulation name must be unique.");
        }

        // Calculate total quantity and target crude protein value from the request
        double totalQuantityFromMainIngredients = feedFormulationSupport2.calculateTotalQuantityFromRequest(request);
        double targetCpValueFromMainIngredients = feedFormulationSupport2.calculateTargetCpValue(request);

        // Build a new FeedFormulation object
        FeedFormulation formulation = FeedFormulation.builder()
                .formulationId(generateGuid()) // Generate a unique ID for the formulation
                .formulationName(request.getFormulationName())
                .date(LocalDate.now()) // Set the current date
                .totalQuantityKg(totalQuantityFromMainIngredients)
                .targetCpValue(targetCpValueFromMainIngredients)
                .build();

        // Create main and other ingredients
        List<Ingredient2> mainIngredients = feedFormulationSupport2.createIngredients(request, formulation);
        List<Ingredient2> otherIngredients = feedFormulationSupport2.createOtherIngredients(totalQuantityFromMainIngredients, formulation);
        List<Ingredient2> allIngredients = new ArrayList<>(mainIngredients);
        allIngredients.addAll(otherIngredients); // Combine all ingredients

        formulation.setIngredient2s(allIngredients); // Set ingredients in the formulation
        return feedFormulationRepository2.save(formulation); // Save the formulation to the database
    }

    /**
     * Retrieves a custom feed formulation by its ID and date.
     *
     * @param id The ID of the feed formulation.
     * @param date The date of the formulation.
     * @return The found FeedFormulation entity.
     */
    @Override
    public FeedFormulation getCustomFormulationByIdAndDate(String id, String date) {
        LocalDate localDate = LocalDate.parse(date); // Parse date from string
        return feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));
    }

    /**
     * Retrieves all custom feed formulations.
     *
     * @return A list of FeedFormulationResponse DTOs.
     */
    @Override
    public List<FeedFormulationResponse> getCustomFormulations() {
        return feedFormulationRepository2.findAll().stream()
                .map(this::mapToResponse) // Map each FeedFormulation to FeedFormulationResponse
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing custom feed formulation by its ID and date.
     *
     * @param id The ID of the feed formulation to update.
     * @param date The date of the formulation.
     * @param request The request object containing updated formulation details.
     * @return The updated FeedFormulation entity.
     */
    @Transactional
    @Override
    public FeedFormulation updateCustomFeedFormulationByIdAndDate(String id, String date, FeedFormulationRequest request) {
        validateRequest(request); // Validate the incoming request

        LocalDate localDate = LocalDate.parse(date);
        FeedFormulation existing = feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));

        // Update existing formulation properties (name, total quantity, and target CP value)
        existing.setFormulationName(request.getFormulationName());
        double newTotalQuantityKg = feedFormulationSupport2.calculateTotalQuantityFromRequest(request);
        double newTargetCpValue = feedFormulationSupport2.calculateTargetCpValue(request);
        existing.setTotalQuantityKg(newTotalQuantityKg);
        existing.setTargetCpValue(newTargetCpValue);

        // Recreate and update new ingredients
        List<Ingredient2> updatedMainIngredients = feedFormulationSupport2.createIngredients(request, existing);
        List<Ingredient2> updatedOtherIngredients = feedFormulationSupport2.createOtherIngredients(newTotalQuantityKg, existing);
        List<Ingredient2> allUpdatedIngredients = new ArrayList<>(updatedMainIngredients);
        allUpdatedIngredients.addAll(updatedOtherIngredients);

        // Clear existing ingredients and set the new ones
        existing.getIngredient2s().clear();
        existing.getIngredient2s().addAll(allUpdatedIngredients);

        // Save and return the updated formulation
        return feedFormulationRepository2.save(existing);
    }


    /**
     * Deletes a custom feed formulation by its ID and date.
     *
     * @param id The ID of the feed formulation to delete.
     * @param date The date of the formulation.
     */
    @Override
    public void deleteCustomFeedFormulationByIdAndDate(String id, String date) {
        LocalDate localDate = LocalDate.parse(date);
        FeedFormulation formulation = feedFormulationRepository2.findByFormulationIdAndDate(id, localDate)
                .orElseThrow(() -> new FeedFormulationNotFoundException("Feed formulation not found for ID: " + id + " and Date: " + date));
        feedFormulationRepository2.delete(formulation); // Delete the formulation from the database
    }

    /**
     * Maps a FeedFormulation entity to a FeedFormulationResponse DTO.
     *
     * @param formulation The FeedFormulation entity to map.
     * @return A FeedFormulationResponse DTO.
     */
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
                .ingredient2s(ingredientDTOs) // Set the list of updated ingredient DTOs
                .build();
    }


    /**
     * Generates a unique identifier for feed formulations using UUID.
     *
     * @return A string representation of the generated GUID.
     */
    private String generateGuid() {
        return UUID.randomUUID().toString(); // Generate a random UUID
    }

    /**
     * Validates the provided FeedFormulationRequest to ensure it has the required ingredients.
     *
     * @param request The FeedFormulationRequest to validate.
     * @throws ValidationException if the request is invalid.
     */
    private void validateRequest(FeedFormulationRequest request) {
        String errorMessage = (request.getProteins() == null || request.getProteins().isEmpty())
                ? "Proteins are required."
                : (request.getCarbohydrates() == null || request.getCarbohydrates().isEmpty())
                ? "Carbohydrates are required."
                : null;

        if (errorMessage != null) {
            throw new ValidationException(errorMessage);
        }
    }
}