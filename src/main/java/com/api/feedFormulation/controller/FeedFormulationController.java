package com.api.feedFormulation.controller;


import com.api.feedFormulation.dto.FeedRequestDTO;
import com.api.feedFormulation.dto.FeedResponseDTO;
import com.api.feedFormulation.service.FeedFormulationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feed-formulation")
@RequiredArgsConstructor
public class FeedFormulationController {

    private final FeedFormulationService feedFormulationService;

    // Create a new feed formulation
    @PostMapping
    public ResponseEntity<FeedResponseDTO> createFormulation(@Valid @RequestBody FeedRequestDTO request) {
        FeedResponseDTO response = feedFormulationService.calculateFeed(request);
        return ResponseEntity.ok(response);
    }

    // Get an existing feed formulation by formulation ID and date
    @GetMapping("/{formulationId}/{date}")
    public ResponseEntity<FeedResponseDTO> getFormulation(@PathVariable String formulationId, @PathVariable String date) {
        FeedResponseDTO response = feedFormulationService.getFeedResponseByFormulationIdAndDate(formulationId, date);
        return ResponseEntity.ok(response);
    }
    // Get all feed formulations
    @GetMapping
    public ResponseEntity<List<FeedResponseDTO>> getAllFormulations() {
        List<FeedResponseDTO> formulations = feedFormulationService.getAllFeedFormulations();
        return ResponseEntity.ok(formulations);
    }


    // Update an existing feed formulation
    @PutMapping("/{formulationId}/{date}")
    public ResponseEntity<FeedResponseDTO> updateFormulation(@PathVariable String formulationId, @PathVariable String date, @Valid @RequestBody FeedRequestDTO request) {
        FeedResponseDTO response = feedFormulationService.updateFeedResponse(formulationId, date, request);
        return ResponseEntity.ok(response);
    }

    // Delete a feed formulation
    @DeleteMapping("/{formulationId}/{date}")
    public ResponseEntity<Void> deleteFormulation(@PathVariable String formulationId, @PathVariable String date) {
        feedFormulationService.deleteFeedResponse(formulationId, date);
        return ResponseEntity.noContent().build();
    }
}
