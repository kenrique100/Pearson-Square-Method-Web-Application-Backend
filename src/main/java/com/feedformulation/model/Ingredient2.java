package com.feedformulation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredient2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double crudeProtein;
    private double quantityKg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "formulation_id")
    @JsonBackReference // Use this to avoid cyclic serialization issues
    private FeedFormulation feedFormulation;
}

