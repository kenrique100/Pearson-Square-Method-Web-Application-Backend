package com.feedformulation.controller;

import com.feedformulation.dto.FeedRequestDTO;
import com.feedformulation.dto.FeedResponseDTO;
import com.feedformulation.service.FeedFormulationService;
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
@RequestMapping("/api/feed-formulation")
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
    public ResponseEntity<FeedResponseDTO> createFeedFormulation(@Valid @RequestBody FeedRequestDTO request) {
        FeedResponseDTO response = feedFormulationService.createFeedFormulation(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to retrieve a feed formulation by its formulationId and date.
     *
     * @param formulationId The ID of the formulation.
     * @param date The date of the formulation.
     * @return The retrieved FeedResponseDTO with the formulation details.
     */
    @GetMapping("/{formulationId}/{date}")
    public ResponseEntity<FeedResponseDTO> getFormulationByIdAndDate(
            @PathVariable String formulationId, @PathVariable String date) {
        FeedResponseDTO response = feedFormulationService.getFormulationByIdAndDate(formulationId, date);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to retrieve all feed formulations.
     *
     * @return A list of all FeedResponseDTOs.
     */
    @GetMapping
    public ResponseEntity<List<FeedResponseDTO>> getFormulations() {
        List<FeedResponseDTO> formulations = feedFormulationService.getFormulations();
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
    public ResponseEntity<FeedResponseDTO> updateFeedFormulationByIdAndDate(
            @PathVariable String formulationId, @PathVariable String date,
            @Valid @RequestBody FeedRequestDTO request) {
        FeedResponseDTO response = feedFormulationService.updateFeedFormulationByIdAndDate(formulationId, date, request);
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
    public ResponseEntity<Void> deleteFeedFormulationByIdAndDate(
            @PathVariable String formulationId, @PathVariable String date) {
        feedFormulationService.deleteFeedFormulationByIdAndDate(formulationId, date);
        return ResponseEntity.noContent().build();
    }
}
