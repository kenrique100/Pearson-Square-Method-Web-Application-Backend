package com.feedformulation.repository;

import com.feedformulation.model.FeedResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedFormulationRepository extends JpaRepository<FeedResponse, Long> {

    Optional<FeedResponse> findByFormulationIdAndDate(String Id, String date);

    boolean existsByFormulationName(String formulationName);
}
