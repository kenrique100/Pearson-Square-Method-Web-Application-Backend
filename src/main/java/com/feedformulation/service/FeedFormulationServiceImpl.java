package com.feedformulation.service;

import com.feedformulation.dto.FeedRequestDTO;
import com.feedformulation.dto.FeedResponseDTO;
import com.feedformulation.exception.InvalidInputException;
import com.feedformulation.model.FeedResponse;
import com.feedformulation.model.Ingredient;
import com.feedformulation.repository.FeedFormulationRepository;
import com.feedformulation.utils.FeedFormulationSupport;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedFormulationServiceImpl implements FeedFormulationService {

    private final FeedFormulationRepository repository;
    private final FeedFormulationSupport support;

    /**
     * Calculates feed formulation based on the given request.
     * Validates the request, generates ingredients, and saves the feed response.
     *
     * @param request The request containing quantity and target crude protein value.
     * @return The saved feed response as a DTO.
     */
    @Transactional
    @Override
    public FeedResponseDTO calculateFeed(FeedRequestDTO request) {
        log.info("Starting feed formulation calculation.");

        if (repository.existsByFormulationName(request.getFormulationName())) {
            throw new InvalidInputException("Formulation name must be unique.");
        }

        // Validate request values
        support.validateRequest(request.getFormulationName(), request.getQuantity(), request.getTargetCpValue());


        // Create ingredients based on quantity
        List<Ingredient> ingredients = support.createIngredients(request.getQuantity());

        // Build FeedResponse entity
        FeedResponse response = getFeedResponse(request, ingredients);

        // Associate ingredients with the feed response
        support.setFeedResponseToIngredients(response, ingredients);

        // Save the FeedResponse entity
        FeedResponse savedResponse = repository.save(response);

        log.info("Feed formulation calculation completed.");
        return support.mapToDTO(savedResponse);
    }

    private FeedResponse getFeedResponse(FeedRequestDTO request, List<Ingredient> ingredients) {
        return FeedResponse.builder()
                .formulationId(support.generateGuid())
                .date(java.time.LocalDate.now().toString())
                .formulationName(request.getFormulationName()) // Set formulation name
                .quantity(request.getQuantity())
                .targetCpValue(request.getTargetCpValue())
                .ingredients(ingredients)
                .build();
    }

    /**
     * Retrieves a feed response by formulation ID and date.
     *
     * @param formulationId The unique ID of the feed formulation.
     * @param date The date of the feed formulation.
     * @return The feed response as a DTO.
     * @throws InvalidInputException if no feed response is found.
     */
    @Override
    public FeedResponseDTO getFeedResponseByFormulationIdAndDate(String formulationId, String date) {
        log.info("Fetching feed formulation with ID: {} and date: {}", formulationId, date);
        FeedResponse response = repository.findByFormulationIdAndDate(formulationId, date)
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));
        return support.mapToDTO(response);
    }

    /**
     * Retrieves all feed formulations.
     *
     * @return A list of all feed formulations as DTOs.
     */
    @Override
    public List<FeedResponseDTO> getAllFeedFormulations() {
        log.info("Fetching all feed formulations.");
        return repository.findAll().stream()
                .map(support::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing feed response based on formulation ID and date.
     *
     * @param formulationId The unique ID of the feed formulation.
     * @param date The date of the feed formulation.
     * @param request The request containing updated quantity and target crude protein value.
     * @return The updated feed response as a DTO.
     * @throws InvalidInputException if the feed response to update is not found.
     */
    @Override
    public FeedResponseDTO updateFeedResponse(String formulationId, String date, FeedRequestDTO request) {
        log.info("Updating feed formulation with ID: {} and date: {}", formulationId, date);
        FeedResponse existingResponse = repository.findByFormulationIdAndDate(formulationId, date)
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));

        // Update existing response fields
        existingResponse.setFormulationName(request.getFormulationName());
        existingResponse.setQuantity(request.getQuantity());
        existingResponse.setTargetCpValue(request.getTargetCpValue());
        return support.mapToDTO(repository.save(existingResponse));
    }

    /**
     * Deletes a feed response based on formulation ID and date.
     *
     * @param formulationId The unique ID of the feed formulation.
     * @param date The date of the feed formulation.
     * @throws InvalidInputException if the feed response to delete is not found.
     */
    @Override
    public void deleteFeedResponse(String formulationId, String date) {
        log.info("Deleting feed formulation with ID: {} and date: {}", formulationId, date);
        FeedResponse response = repository.findByFormulationIdAndDate(formulationId, date)
                .orElseThrow(() -> new InvalidInputException("Feed formulation not found"));
        repository.delete(response);
    }
}
