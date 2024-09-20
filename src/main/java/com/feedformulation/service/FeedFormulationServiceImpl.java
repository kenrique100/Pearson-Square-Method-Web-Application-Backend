package com.feedformulation.service;

import com.feedformulation.dto.FeedRequestDTO;
import com.feedformulation.dto.FeedResponseDTO;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.exception.InvalidInputException;
import com.feedformulation.model.FeedResponse;
import com.feedformulation.model.Ingredient;
import com.feedformulation.repository.FeedFormulationRepository;
import com.feedformulation.utils.FeedFormulationSupport;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the FeedFormulationService interface.
 * Handles the business logic for creating, retrieving, updating, and deleting feed formulations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FeedFormulationServiceImpl implements FeedFormulationService {

    private final FeedFormulationRepository repository;
    private final FeedFormulationSupport support;

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

        // Check if the formulation name is unique
        if (repository.existsByFormulationName(request.getFormulationName())) {
            throw new InvalidInputException("Formulation name must be unique.");
        }

        // Validate the input quantity and target CP value
        support.validateRequest(request.getQuantity(), request.getTargetCpValue());

        // Create the list of ingredients based on the quantity
        List<Ingredient> ingredients = support.createIngredients(request.getQuantity());

        // Build the FeedResponse object with the formulation details
        FeedResponse response = getFeedResponse(request, ingredients);
        support.setFeedResponseToIngredients(response, ingredients);

        // Save the feed formulation and return the response DTO
        FeedResponse savedResponse = repository.save(response);
        log.info("Feed formulation calculation completed.");
        return support.mapToDTO(savedResponse);
    }

    /**
     * Helper method to create a FeedResponse object.
     *
     * @param request The FeedRequestDTO containing the formulation details.
     * @param ingredients The list of ingredients to be included in the formulation.
     * @return A FeedResponse object with the formulation details.
     */
    private FeedResponse getFeedResponse(FeedRequestDTO request, List<Ingredient> ingredients) {
        double totalQuantityKg = ingredients.stream().mapToDouble(Ingredient::getQuantity).sum(); // Calculate total quantity
        return FeedResponse.builder()
                .formulationId(support.generateGuid())
                .date(LocalDate.now().toString())
                .formulationName(request.getFormulationName())
                .quantity(totalQuantityKg) // Set total quantity here
                .targetCpValue(request.getTargetCpValue())
                .ingredients(ingredients)
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
        LocalDate localDate = LocalDate.parse(date);

        // Find the formulation by its ID and date, throw an exception if not found
        return repository.findByFormulationIdAndDate(formulationId, String.valueOf(localDate))
                .map(support::mapToDTO)
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
                .map(support::mapToDTO)
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

        // Find the existing formulation by its ID and date, throw an exception if not found
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

        // Find the formulation by its ID and date, throw an exception if not found
        FeedResponse response = repository.findByFormulationIdAndDate(formulationId, String.valueOf(LocalDate.parse(date)))
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));

        // Delete the found formulation
        repository.delete(response);
    }
}
