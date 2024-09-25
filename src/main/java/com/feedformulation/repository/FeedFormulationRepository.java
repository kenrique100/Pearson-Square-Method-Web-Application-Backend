package com.feedformulation.repository;

import com.feedformulation.model.FeedResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for FeedFormulation entity.
 * This interface provides CRUD operations and custom query methods for the FeedFormulation entity by extending JpaRepository.
 */
@Repository
public interface FeedFormulationRepository extends JpaRepository<FeedResponse, Long> {

    /**
     * Custom query method to find a feed formulation by its unique ID and date.
     * This method returns an Optional containing the FeedResponse if found, or an empty Optional if not.
     *
     * @param id The unique identifier for the feed formulation.
     * @param date The date of the feed formulation.
     * @return An Optional containing the FeedResponse entity if found.
     */
    Optional<FeedResponse> findByFormulationIdAndDate(String id, String date);

    /**
     * Custom method to check if a feed formulation exists with a given formulation name.
     * Returns true if a formulation with the specified name exists, false otherwise.
     *
     * @param formulationName The name of the feed formulation to check.
     * @return A boolean indicating the existence of a feed formulation with the given name.
     */
    boolean existsByFormulationName(String formulationName);
}
