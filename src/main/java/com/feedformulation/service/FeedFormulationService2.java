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
     * @return The newly created CustomFeedFormulation entity
     */
    FeedFormulation createFeedFormulation(FeedFormulationRequest request);

    /**
     * Retrieves a feed formulation by its unique ID.
     *
     * @param id The unique identifier of the feed formulation
     * @return The CustomFeedFormulation entity corresponding to the provided ID
     */
    FeedFormulation getFeedFormulationById(String id);

    /**
     * Retrieves a list of all feed formulations.
     *
     * @return A list of all CustomFeedFormulation entities
     */
    List<FeedFormulationResponse> getAllFeedFormulations();

    /**
     * Updates an existing feed formulation with the provided data.
     *
     * @param id The unique identifier of the feed formulation to be updated
     * @param request The request containing the updated data for the feed formulation
     * @return The updated CustomFeedFormulation entity
     */
    FeedFormulation updateFeedFormulation(String id, FeedFormulationRequest request);

    /**
     * Deletes a feed formulation by its unique ID.
     *
     * @param id The unique identifier of the feed formulation to be deleted
     */
    void deleteFeedFormulation(String id);

}
