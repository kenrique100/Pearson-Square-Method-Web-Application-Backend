package com.feedformulation.repository;

import com.feedformulation.model.FeedFormulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedFormulationRepository2 extends JpaRepository<FeedFormulation, String> {
    // This interface automatically inherits CRUD operations from JpaRepository for CustomFeedFormulation entities.
}
