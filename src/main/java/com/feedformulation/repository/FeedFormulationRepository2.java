package com.feedformulation.repository;

import com.feedformulation.model.FeedFormulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FeedFormulationRepository2 extends JpaRepository<FeedFormulation, String> {

    // Query method to find a FeedFormulation by formulationId and date
    Optional<FeedFormulation> findByFormulationIdAndDate(String id, LocalDate localDate);
}
