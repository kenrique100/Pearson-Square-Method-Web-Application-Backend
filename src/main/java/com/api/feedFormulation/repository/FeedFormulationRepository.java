package com.api.feedFormulation.repository;

import com.api.feedFormulation.model.FeedResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedFormulationRepository extends JpaRepository<FeedResponse, Long> {
    Optional<FeedResponse> findByFormulationIdAndDate(String formulationId, String date);
}
