package com.feedformulation.service;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.dto.FeedFormulationResponse;
import com.feedformulation.model.FeedFormulation;

import java.util.List;

public interface FeedFormulationService2 {

    /**
     * Creates a new feed formulation based on the provided request.
     *
     * @param request The request containing the data needed to create the feed formulation
     * @return The newly created FeedFormulation entity
     */
    FeedFormulation createFeedFormulation(FeedFormulationRequest request);

    /**
     * Retrieves a feed formulation by its unique ID and date.
     *
     * @param id   The unique identifier of the feed formulation
     * @param date The date of the formulation.
     * @return The FeedFormulation entity corresponding to the provided ID and date
     */
    FeedFormulation getFeedFormulationByIdAndDate(String id, String date);

    /**
     * Retrieves a list of all feed formulations.
     *
     * @return A list of all FeedFormulationResponse entities
     */
    List<FeedFormulationResponse> getAllFeedFormulations();

    /**
     * Updates an existing feed formulation with the provided data, identified by ID and date.
     *
     * @param id      The unique identifier of the feed formulation to be updated
     * @param date    The date of the formulation to be updated
     * @param request The request containing the updated data for the feed formulation
     * @return The updated FeedFormulation entity
     */
    FeedFormulation updateFeedFormulationByIdAndDate(String id, String date, FeedFormulationRequest request);

    /**
     * Deletes a feed formulation by its unique ID and date.
     *
     * @param id   The unique identifier of the feed formulation to be deleted
     * @param date The date of the formulation to be deleted
     */
    void deleteFeedFormulationByIdAndDate(String id, String date);

}
