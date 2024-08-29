package com.api.feedFormulation.service;

import com.api.feedFormulation.dto.FeedRequestDTO;
import com.api.feedFormulation.dto.FeedResponseDTO;

import java.util.List;

public interface FeedFormulationService {
    FeedResponseDTO calculateFeed(FeedRequestDTO request);
    FeedResponseDTO getFeedResponseByFormulationIdAndDate(String formulationId, String date);
    FeedResponseDTO updateFeedResponse(String formulationId, String date, FeedRequestDTO request);
    void deleteFeedResponse(String formulationId, String date);
    List<FeedResponseDTO> getAllFeedFormulations();
}
