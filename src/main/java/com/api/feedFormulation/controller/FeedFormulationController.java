package com.api.feedFormulation.controller;

import com.api.feedFormulation.dto.FeedRequestDTO;
import com.api.feedFormulation.dto.FeedResponseDTO;
import com.api.feedFormulation.service.FeedFormulationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing feed formulations.
 * This class handles incoming HTTP requests, delegates processing to the service layer,
 * and returns appropriate HTTP responses.
 */
@RestController
@RequestMapping("/api/v1/feed-formulation")
@RequiredArgsConstructor
public class FeedFormulationController {

    private final FeedFormulationService feedFormulationService;

    /**
     * Endpoint to create a new feed formulation.
     *
     * @param request The FeedRequestDTO containing the details for the formulation.
     * @return The created FeedResponseDTO with the formulation details.
     */
    @PostMapping
    public ResponseEntity<FeedResponseDTO> createFormulation(@Valid @RequestBody FeedRequestDTO request) {
        FeedResponseDTO response = feedFormulationService.calculateFeed(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to retrieve a feed formulation by its formulation ID and date.
     *
     * @param formulationId The ID of the formulation.
     * @param date The date of the formulation.
     * @return The retrieved FeedResponseDTO with the formulation details.
     */
    @GetMapping("/{formulationId}/{date}")
    public ResponseEntity<FeedResponseDTO> getFormulation(@PathVariable String formulationId, @PathVariable String date) {
        FeedResponseDTO response = feedFormulationService.getFeedResponseByFormulationIdAndDate(formulationId, date);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to retrieve all feed formulations.
     *
     * @return A list of all FeedResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<List<FeedResponseDTO>> getAllFormulations() {
        List<FeedResponseDTO> formulations = feedFormulationService.getAllFeedFormulations();
        return ResponseEntity.ok(formulations);
    }

    /**
     * Endpoint to update an existing feed formulation.
     *
     * @param formulationId The ID of the formulation to be updated.
     * @param date The date of the formulation to be updated.
     * @param request The FeedRequestDTO containing the new details for the formulation.
     * @return The updated FeedResponseDTO with the new formulation details.
     */
    @PutMapping("/{formulationId}/{date}")
    public ResponseEntity<FeedResponseDTO> updateFormulation(@PathVariable String formulationId, @PathVariable String date, @Valid @RequestBody FeedRequestDTO request) {
        FeedResponseDTO response = feedFormulationService.updateFeedResponse(formulationId, date, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to delete a feed formulation.
     *
     * @param formulationId The ID of the formulation to be deleted.
     * @param date The date of the formulation to be deleted.
     * @return A response entity with no content.
     */
    @DeleteMapping("/{formulationId}/{date}")
    public ResponseEntity<Void> deleteFormulation(@PathVariable String formulationId, @PathVariable String date) {
        feedFormulationService.deleteFeedResponse(formulationId, date);
        return ResponseEntity.noContent().build();
    }
}
