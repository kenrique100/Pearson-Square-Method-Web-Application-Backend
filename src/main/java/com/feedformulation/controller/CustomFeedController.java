package com.feedformulation.controller;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.dto.FeedFormulationResponse;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.service.FeedFormulationService2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST controller to handle HTTP requests and responses
@RequestMapping("/api/feed-formulations") // Defines the base URL path for all endpoints in this controller
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required fields (final fields) injected automatically
public class CustomFeedController {

    private final FeedFormulationService2 feedFormulationService2; // Service responsible for the business logic related to feed formulations

    /**
     * Creates a new custom feed formulation based on the request.
     *
     * @param request DTO containing the input data for the feed formulation
     * @return HTTP 200 OK response with the created feed formulation object in the response body
     */
    @PostMapping // Maps HTTP POST requests to this method
    public ResponseEntity<FeedFormulation> createFeedFormulation(@RequestBody FeedFormulationRequest request) {
        // Calls the service to create a feed formulation
        FeedFormulation formulation = feedFormulationService2.createFeedFormulation(request);
        // Returns HTTP 200 OK with the newly created formulation
        return ResponseEntity.ok(formulation);
    }

    /**
     * Retrieves a feed formulation by its unique ID.
     *
     * @param id The unique identifier for the feed formulation
     * @return HTTP 200 OK response with the corresponding feed formulation object
     */
    @GetMapping("/{id}") // Maps HTTP GET requests with a dynamic path variable (id) to this method
    public ResponseEntity<FeedFormulation> getFeedFormulationById(@PathVariable String id) {
        // Fetches the feed formulation using the service by ID and returns it in the response
        return ResponseEntity.ok(feedFormulationService2.getFeedFormulationById(id));
    }

    /**
     * Retrieves all existing custom feed formulations.
     *
     * @return HTTP 200 OK response with a list of all feed formulations
     */
    @GetMapping
    public ResponseEntity<List<FeedFormulationResponse>> getAllFeedFormulations() {
        List<FeedFormulationResponse> formulations = feedFormulationService2.getAllFeedFormulations();
        return ResponseEntity.ok(formulations);
    }

    /**
     * Updates an existing feed formulation by its unique ID.
     *
     * @param id The unique identifier for the feed formulation to update
     * @param request DTO containing the updated data for the feed formulation
     * @return HTTP 200 OK response with the updated feed formulation object
     */
    @PutMapping("/{id}") // Maps HTTP PUT requests with a dynamic path variable (id) to this method
    public ResponseEntity<FeedFormulation> updateFeedFormulation(@PathVariable String id,
                                                                 @RequestBody FeedFormulationRequest request) {
        // Updates the feed formulation with the provided data using the service
        return ResponseEntity.ok(feedFormulationService2.updateFeedFormulation(id, request));
    }

    /**
     * Deletes a feed formulation by its unique ID.
     *
     * @param id The unique identifier for the feed formulation to delete
     * @return HTTP 204 No Content response after successful deletion
     */
    @DeleteMapping("/{id}") // Maps HTTP DELETE requests with a dynamic path variable (id) to this method
    public ResponseEntity<Void> deleteFeedFormulation(@PathVariable String id) {
        // Calls the service to delete the feed formulation by ID
        feedFormulationService2.deleteFeedFormulation(id);
        // Returns HTTP 204 No Content to indicate the deletion was successful
        return ResponseEntity.noContent().build();
    }
}
