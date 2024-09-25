package com.feedformulation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a feed formulation.
 * This class is mapped to a database table and contains details of the feed formulation,
 * such as its name, target crude protein value, total quantity, and associated ingredients.
 */
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedFormulation {

    /**
     * Unique identifier for each feed formulation.
     * The ID is represented as a string (UUID) and must be unique and non-null.
     */
    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String formulationId;

    /**
     * The unique name of the feed formulation.
     * This column is unique and cannot be null.
     */
    @Column(unique = true, nullable = false)
    private String formulationName;

    /**
     * The date when the formulation was created.
     * Defaults to the current date if not provided.
     */
    @Builder.Default
    @Column(nullable = false)
    private LocalDate date = LocalDate.now(); // Default value for the date field

    /**
     * The target crude protein (CP) value for the formulation.
     * This value must be specified and cannot be null.
     */
    @Column(nullable = false)
    private double targetCpValue;

    /**
     * The total quantity of the feed formulation in kilograms.
     * Stored in the database under the column name "total_quantity_kg".
     */
    @Column(name = "total_quantity_kg")
    private double totalQuantityKg;

    /**
     * The list of ingredients associated with the feed formulation.
     * Ingredients are mapped using a one-to-many relationship with FeedFormulation.
     * CascadeType.ALL ensures that changes to FeedFormulation are cascaded to its ingredients,
     * and orphanRemoval ensures that unused ingredients are deleted.
     * FetchType.LAZY is used to load ingredients only when needed.
     * JsonManagedReference helps prevent cyclic reference issues during JSON serialization.
     */
    @Builder.Default
    @OneToMany(mappedBy = "feedFormulation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // Prevents cyclic reference issue with JSON serialization
    private List<Ingredient2> ingredient2s = new ArrayList<>(); // Default empty list for ingredients
}
