package com.feedformulation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an ingredient used in feed formulation.
 * This class is mapped to a database table and is associated with a specific feed formulation.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredient2 {

    /**
     * Unique identifier for each ingredient.
     * This ID is auto-generated using the IDENTITY strategy.
     * Marked with @JsonIgnore to prevent exposure of the ID in API responses.
     */
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the ingredient, such as "Corn" or "Soybean Meal".
     */
    private String name;

    /**
     * The crude protein (CP) content of the ingredient, used in calculating the total CP value of the feed formulation.
     */
    private double crudeProtein;

    /**
     * The quantity of this ingredient in kilograms.
     */
    private double quantityKg;

    /**
     * The feed formulation this ingredient is associated with.
     * This is a many-to-one relationship, as a feed formulation can have multiple ingredients.
     * EAGER fetch type is used to load the related feed formulation when this entity is queried.
     * JsonBackReference is used to avoid cyclic serialization issues between Ingredient2 and FeedFormulation.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "formulation_id")
    @JsonBackReference // Prevents cyclic reference issue with JSON serialization
    private FeedFormulation feedFormulation;
}
