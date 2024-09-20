package com.feedformulation.controller;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.dto.FeedFormulationResponse;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.service.FeedFormulationService2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feed-formulations")
@RequiredArgsConstructor
public class CustomFeedController {

    private final FeedFormulationService2 feedFormulationService2;

    /**
     * Creates a new custom feed formulation based on the request.
     *
     * @param request DTO containing the input data for the feed formulation
     * @return HTTP 200 OK response with the created feed formulation object in the response body
     */
    @PostMapping
    public ResponseEntity<FeedFormulation> createCustomFormulation(@RequestBody FeedFormulationRequest request) {
        FeedFormulation formulation = feedFormulationService2.createCustomFormulation(request);
        return ResponseEntity.ok(formulation);
    }

    /**
     * Retrieves a feed formulation by its unique ID and date.
     *
     * @param id The unique identifier for the feed formulation
     * @param date The date of the formulation in yyyy-MM-dd format.
     * @return HTTP 200 OK response with the corresponding feed formulation object
     */
    @GetMapping("/{id}/{date}")
    public ResponseEntity<FeedFormulation> getCustomFormulationByIdAndDate(@PathVariable String id, @PathVariable String date) {
        FeedFormulation formulation = feedFormulationService2.getCustomFormulationByIdAndDate(id, date);
        return ResponseEntity.ok(formulation);
    }

    /**
     * Retrieves all existing custom feed formulations.
     *
     * @return HTTP 200 OK response with a list of all feed formulations
     */
    @GetMapping
    public ResponseEntity<List<FeedFormulationResponse>> getCustomFormulations() {
        List<FeedFormulationResponse> formulations = feedFormulationService2.getCustomFormulations();
        return ResponseEntity.ok(formulations);
    }

    /**
     * Updates an existing feed formulation by its unique ID and date.
     *
     * @param id The unique identifier for the feed formulation to update
     * @param date The date of the formulation in yyyy-MM-dd format.
     * @param request DTO containing the updated data for the feed formulation
     * @return HTTP 200 OK response with the updated feed formulation object
     */
    @PutMapping("/{id}/{date}")
    public ResponseEntity<FeedFormulation> updateCustomFeedFormulationByIdAndDate(@PathVariable String id,
                                                                            @PathVariable String date,
                                                                            @RequestBody FeedFormulationRequest request) {
        FeedFormulation updatedFormulation = feedFormulationService2.updateCustomFeedFormulationByIdAndDate(id, date, request);
        return ResponseEntity.ok(updatedFormulation);
    }

    /**
     * Deletes a feed formulation by its unique ID and date.
     *
     * @param id The unique identifier for the feed formulation to delete
     * @param date The date of the formulation in yyyy-MM-dd format.
     * @return HTTP 204 No Content response after successful deletion
     */
    @DeleteMapping("/{id}/{date}")
    public ResponseEntity<Void> deleteCustomFeedFormulationByIdAndDate(@PathVariable String id, @PathVariable String date) {
        feedFormulationService2.deleteCustomFeedFormulationByIdAndDate(id, date);
        return ResponseEntity.noContent().build();
    }
}
