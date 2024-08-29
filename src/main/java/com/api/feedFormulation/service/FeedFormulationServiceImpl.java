package com.api.feedFormulation.service;

import com.api.feedFormulation.dto.FeedRequestDTO;
import com.api.feedFormulation.dto.FeedResponseDTO;
import com.api.feedFormulation.dto.IngredientDTO;
import com.api.feedFormulation.exception.InvalidInputException;
import com.api.feedFormulation.model.*;
import com.api.feedFormulation.repository.FeedFormulationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the FeedFormulationService interface that handles business logic
 * related to feed formulations. This service interacts with the repository to manage
 * feed formulations and convert between entities and DTOs.
 */
@Service
@RequiredArgsConstructor
public class FeedFormulationServiceImpl implements FeedFormulationService {

    private final FeedFormulationRepository repository;

    // Calculates and saves a new feed formulation based on the provided request.
    @Transactional
    @Override
    public FeedResponseDTO calculateFeed(FeedRequestDTO request) {
        validateRequest(request);

        // Create a list of ingredients based on the provided quantity
        List<Ingredient> ingredients = createIngredients(request.getQuantity());

        // Build a FeedResponse entity with the calculated ingredients
        FeedResponse response = FeedResponse.builder()
                .formulationId(generateGuid()) // Generate a unique ID for the formulation
                .date(LocalDate.now().toString()) // Set the current date
                .quantity(request.getQuantity())
                .targetCpValue(request.getTargetCpValue())
                .ingredients(ingredients)
                .build();

        // Set bidirectional relationship between FeedResponse and ingredients
        setFeedResponseToIngredients(response, ingredients);

        // Save the FeedResponse entity and return its DTO representation
        return mapToDTO(repository.save(response));
    }

    // Ensures bidirectional consistency by setting the FeedResponse reference in each ingredient.
    private void setFeedResponseToIngredients(FeedResponse feedResponse, List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            ingredient.setFeedResponse(feedResponse);
        }
    }

    // Retrieves a feed formulation by its formulation ID and date.
    @Override
    public FeedResponseDTO getFeedResponseByFormulationIdAndDate(String formulationId, String date) {
        FeedResponse response = repository.findByFormulationIdAndDate(formulationId, date)
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));
        return mapToDTO(response);
    }

    // Retrieves all feed formulations from the database.
    @Override
    public List<FeedResponseDTO> getAllFeedFormulations() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Updates an existing feed formulation based on the provided request.
    @Override
    public FeedResponseDTO updateFeedResponse(String formulationId, String date, FeedRequestDTO request) {
        FeedResponse existingResponse = repository.findByFormulationIdAndDate(formulationId, date)
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));

        // Update the existing feed formulation with the new details
        existingResponse.setQuantity(request.getQuantity());
        existingResponse.setTargetCpValue(request.getTargetCpValue());
        return mapToDTO(repository.save(existingResponse));
    }

    // Deletes a feed formulation based on the formulation ID and date.
    @Override
    public void deleteFeedResponse(String formulationId, String date) {
        FeedResponse response = repository.findByFormulationIdAndDate(formulationId, date)
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));
        repository.delete(response);
    }

    // Validates the feed request DTO to ensure it contains valid data.
    private void validateRequest(FeedRequestDTO request) {
        if (request.getQuantity() <= 0 || request.getQuantity() > 1000) {
            throw new InvalidInputException("Quantity must be greater than zero and not exceed 1000 kg.");
        }
        if (request.getTargetCpValue() <= 0) {
            throw new InvalidInputException("Target CP value must be greater than zero.");
        }
    }

    // Creates a list of ingredients based on the specified quantity.
    private List<Ingredient> createIngredients(double quantity) {
        return List.of(
                new Ingredient("Soya beans", 44.0, quantity * 0.3),
                new Ingredient("Groundnuts", 45.0, quantity * 0.1),
                new Ingredient("Blood Meal", 80.0, quantity * 0.05),
                new Ingredient("Fish Meal", 65.0, quantity * 0.1),
                new Ingredient("Maize", 9.0, quantity * 0.2),
                new Ingredient("Cassava", 2.0, quantity * 0.1),
                new Ingredient("Diphosphate Calcium", 0.0, quantity * 0.02),
                new Ingredient("Bone Meal", 0.0, quantity * 0.02),
                new Ingredient("Marine Shell Flour", 0.0, quantity * 0.02),
                new Ingredient("Salt", 0.0, quantity * 0.005),
                new Ingredient("Vitamin C", 0.0, quantity * 0.005),
                new Ingredient("Premix", 0.0, quantity * 0.01),
                new Ingredient("Concentrate", 36.0, quantity * 0.05),
                new Ingredient("Palm Oil", 0.0, quantity * 0.02),
                new Ingredient("Anti-toxin", 0.0, quantity * 0.0005)
        );
    }

    // Maps a FeedResponse entity to its DTO representation.
    private FeedResponseDTO mapToDTO(FeedResponse feedResponse) {
        List<IngredientDTO> ingredientDTOs = feedResponse.getIngredients()
                .stream()
                .map(ingredient -> IngredientDTO.builder()
                        .name(ingredient.getName())
                        .crudeProtein(ingredient.getCrudeProtein())
                        .quantity(ingredient.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return FeedResponseDTO.builder()
                .formulationId(feedResponse.getFormulationId())
                .date(feedResponse.getDate())
                .quantity(feedResponse.getQuantity())
                .targetCpValue(feedResponse.getTargetCpValue())
                .ingredients(ingredientDTOs)
                .build();
    }

    // Generates a unique identifier for a feed formulation.
    private String generateGuid() {
        return UUID.randomUUID().toString().substring(0, 5);
    }
}
