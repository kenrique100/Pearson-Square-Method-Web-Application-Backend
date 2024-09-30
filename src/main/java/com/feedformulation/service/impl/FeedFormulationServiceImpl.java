package com.feedformulation.service.impl;

import com.feedformulation.dto.FeedRequestDTO;
import com.feedformulation.dto.FeedResponseDTO;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.exception.InvalidInputException;
import com.feedformulation.model.FeedResponse;
import com.feedformulation.model.Ingredient;
import com.feedformulation.repository.FeedFormulationRepository;
import com.feedformulation.service.FeedFormulationService;
import com.feedformulation.utils.FeedFormulationSupport;
import com.feedformulation.utils.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the FeedFormulationService interface.
 * This service handles the business logic for creating, retrieving, updating, and deleting feed formulations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FeedFormulationServiceImpl implements FeedFormulationService {

    private final FeedFormulationRepository repository;  // Repository for interacting with the database
    private final FeedFormulationSupport support;        // Utility class for supporting formulation calculations

    /**
     * Creates a new feed formulation based on the request data.
     *
     * @param request The FeedRequestDTO containing the details for the formulation.
     * @return The created FeedResponseDTO with the formulation details.
     */
    @Transactional
    @Override
    public FeedResponseDTO createFeedFormulation(FeedRequestDTO request) {
        log.info("Starting feed formulation calculation.");

        // Validate formulation name for valid and invalid formulation name
        String formulationName = request.getFormulationName();
        boolean isFormulationNameInvalid = formulationName == null || formulationName.trim().isEmpty() || repository.existsByFormulationName(formulationName);

        if (isFormulationNameInvalid) {
            throw new InvalidInputException(formulationName == null || formulationName.trim().isEmpty()
                    ? "Formulation name cannot be empty."
                    : "Formulation name must be unique.");
        }


        // Use the utility method for validation
        ValidationUtil.validateQuantityAndCpValue(request.getQuantity(), request.getTargetCpValue());

        // Validate other inputs using your support utility methods if needed
        support.validateRequest(request.getQuantity(), request.getTargetCpValue());

        // Create the list of ingredients based on the specified quantity
        List<Ingredient> ingredients = support.createIngredients(request.getQuantity());

        // Build the FeedResponse object containing the formulation details
        FeedResponse response = getFeedResponse(request, ingredients);
        support.setFeedResponseToIngredients(response, ingredients);  // Set formulation details to ingredients

        // Save the feed formulation and return the response DTO
        FeedResponse savedResponse = repository.save(response);
        log.info("Feed formulation calculation completed.");
        return support.mapToDTO(savedResponse);  // Map to DTO for response
    }


    /**
     * Helper method to create a FeedResponse object based on request and ingredients.
     *
     * @param request The FeedRequestDTO containing the formulation details.
     * @param ingredients The list of ingredients to be included in the formulation.
     * @return A FeedResponse object with the formulation details.
     */
    private FeedResponse getFeedResponse(FeedRequestDTO request, List<Ingredient> ingredients) {
        double totalQuantityKg = ingredients.stream().mapToDouble(Ingredient::getQuantity).sum(); // Calculate total quantity
        return FeedResponse.builder()
                .formulationId(support.generateGuid())  // Generate a unique ID for the formulation
                .date(LocalDate.now().toString())       // Get the current date
                .formulationName(request.getFormulationName())
                .quantity(totalQuantityKg)               // Set total quantity here
                .targetCpValue(request.getTargetCpValue())
                .ingredients(ingredients)                // Add the ingredients
                .build();
    }

    /**
     * Retrieves a feed formulation by its formulationId and date.
     *
     * @param formulationId The ID of the formulation.
     * @param date The date of the formulation.
     * @return The retrieved FeedResponseDTO with the formulation details.
     */
    @Override
    public FeedResponseDTO getFormulationByIdAndDate(String formulationId, String date) {
        LocalDate localDate = LocalDate.parse(date); // Parse the date string to LocalDate

        // Find the formulation by its ID and date, throwing an exception if not found
        return repository.findByFormulationIdAndDate(formulationId, String.valueOf(localDate))
                .map(support::mapToDTO) // Map to DTO if found
                .orElseThrow(() -> new FeedFormulationNotFoundException(
                        "Feed formulation not found for ID: " + formulationId + " and Date: " + date));
    }

    /**
     * Retrieves all feed formulations.
     *
     * @return A list of all FeedResponseDTOs.
     */
    @Override
    public List<FeedResponseDTO> getFormulations() {
        log.info("Fetching all feed formulations.");

        // Fetch all formulations from the repository and map them to DTOs
        return repository.findAll().stream()
                .map(support::mapToDTO) // Use mapping utility to convert to DTO
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing feed formulation by its formulationId and date.
     *
     * @param formulationId The ID of the formulation to be updated.
     * @param date The date of the formulation to be updated.
     * @param request The FeedRequestDTO containing the updated details for the formulation.
     * @return The updated FeedResponseDTO with the new formulation details.
     */
    @Override
    public FeedResponseDTO updateFeedFormulationByIdAndDate(String formulationId, String date, FeedRequestDTO request) {
        log.info("Updating feed formulation with ID: {} and date: {}", formulationId, date);

        // Use the utility method for validation of formulation quantity and cp value
        ValidationUtil.validateQuantityAndCpValue(request.getQuantity(), request.getTargetCpValue());

        // Find the existing formulation by its ID and date; throw an exception if not found
        FeedResponse existingResponse = repository.findByFormulationIdAndDate(formulationId, String.valueOf(LocalDate.parse(date)))
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));

        // Update the formulation details
        existingResponse.setFormulationName(request.getFormulationName());
        existingResponse.setQuantity(request.getQuantity());
        existingResponse.setTargetCpValue(request.getTargetCpValue());

        // Save and return the updated formulation as a DTO
        return support.mapToDTO(repository.save(existingResponse));
    }


    /**
     * Deletes a feed formulation by its formulationId and date.
     *
     * @param formulationId The ID of the formulation to be deleted.
     * @param date The date of the formulation to be deleted.
     */
    @Override
    public void deleteFeedFormulationByIdAndDate(String formulationId, String date) {
        log.info("Deleting feed formulation with ID: {} and date: {}", formulationId, date);

        // Find the formulation by its ID and date; throw an exception if not found
        FeedResponse response = repository.findByFormulationIdAndDate(formulationId, String.valueOf(LocalDate.parse(date)))
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));

        // Delete the found formulation
        repository.delete(response); // Perform deletion
    }
}
