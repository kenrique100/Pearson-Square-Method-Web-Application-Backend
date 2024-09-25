package com.feedformulation.repository;

import com.feedformulation.model.FeedFormulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FeedFormulationRepository2 extends JpaRepository<FeedFormulation, String> {

    /**
     * Custom query method to find a feed formulation by its unique ID and date.
     * This method returns an Optional containing the FeedResponse if found, or an empty Optional if not.
     *
     * @param id The unique identifier for the feed formulation.
     * @param localDate The date of the feed formulation.
     * @return An Optional containing the FeedResponse entity if found.
     */
    Optional<FeedFormulation> findByFormulationIdAndDate(String id, LocalDate localDate);

    /**
     * Custom method to check if a feed formulation exists with a given formulation name.
     * Returns true if a formulation with the specified name exists, false otherwise.
     *
     * @param formulationName The name of the feed formulation to check.
     * @return A boolean indicating the existence of a feed formulation with the given name.
     */
    boolean existsByFormulationName(String formulationName);
}
