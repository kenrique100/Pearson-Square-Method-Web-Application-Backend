package com.feedformulation.service;

import com.feedformulation.dto.FeedRequestDTO;
import com.feedformulation.dto.FeedResponseDTO;

import java.util.List;

/**
 * Service interface for managing feed formulations.
 * This interface defines the operations related to creating, retrieving, updating,
 * and deleting feed formulations, as well as fetching all formulations.
 */
public interface FeedFormulationService {

    /**
     * Creates a new feed formulation.
     *
     * @param request The FeedRequestDTO containing the details for the formulation.
     * @return The created FeedResponseDTO with the formulation details.
     */
    FeedResponseDTO createFeedFormulation(FeedRequestDTO request);

    /**
     * Retrieves a feed formulation by its formulationId and date.
     *
     * @param formulationId The ID of the formulation.
     * @param date The date of the formulation.
     * @return The retrieved FeedResponseDTO with the formulation details.
     */
    FeedResponseDTO getFormulationByIdAndDate(String formulationId, String date);

    /**
     * Retrieves all feed formulations.
     *
     * @return A list of all FeedResponseDTOs.
     */
    List<FeedResponseDTO> getFormulations();

    /**
     * Updates an existing feed formulation by its formulationId and date.
     *
     * @param formulationId The ID of the formulation to be updated.
     * @param date The date of the formulation to be updated.
     * @param request The FeedRequestDTO containing the new details for the formulation.
     * @return The updated FeedResponseDTO with the new formulation details.
     */
    FeedResponseDTO updateFeedFormulationByIdAndDate(String formulationId, String date, FeedRequestDTO request);

    /**
     * Deletes a feed formulation by its formulationId and date.
     *
     * @param formulationId The ID of the formulation to be deleted.
     * @param date The date of the formulation to be deleted.
     */
    void deleteFeedFormulationByIdAndDate(String formulationId, String date);
}
