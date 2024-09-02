package com.api.feedFormulation.service;

import com.api.feedFormulation.dto.FeedRequestDTO;
import com.api.feedFormulation.dto.FeedResponseDTO;

import java.util.List;

/**
 * Service interface for managing feed formulations.
 * This interface defines the contract for feed formulation-related operations.
 */
public interface FeedFormulationService {

    /**
     * Calculate and create a feed formulation based on the provided request details.
     *
     * @param request The FeedRequestDTO containing the details for the formulation.
     * @return The created FeedResponseDTO with the formulation details.
     */
    FeedResponseDTO calculateFeed(FeedRequestDTO request);

    /**
     * Retrieve a feed formulation by its formulation ID and date.
     *
     * @param formulationId The ID of the formulation.
     * @param date The date of the formulation.
     * @return The retrieved FeedResponseDTO with the formulation details.
     */
    FeedResponseDTO getFeedResponseByFormulationIdAndDate(String formulationId, String date);

    /**
     * Retrieve all feed formulations.
     *
     * @return A list of all FeedResponseDTOs.
     */
    List<FeedResponseDTO> getAllFeedFormulations();

    /**
     * Update an existing feed formulation with new details.
     *
     * @param formulationId The ID of the formulation to be updated.
     * @param date The date of the formulation to be updated.
     * @param request The FeedRequestDTO containing the new details for the formulation.
     * @return The updated FeedResponseDTO with the new formulation details.
     */
    FeedResponseDTO updateFeedResponse(String formulationId, String date, FeedRequestDTO request);

    /**
     * Delete a feed formulation by its formulation ID and date.
     *
     * @param formulationId The ID of the formulation to be deleted.
     * @param date The date of the formulation to be deleted.
     */
    void deleteFeedResponse(String formulationId, String date);
}
