/*
package com.feedformulation.service.impl;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.model.Ingredient2;
import com.feedformulation.repository.FeedFormulationRepository2;
import com.feedformulation.utils.FeedFormulationSupport2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedFormulationService2ImplTest {

    @Mock
    private FeedFormulationRepository2 feedFormulationRepository2;

    @Mock
    private FeedFormulationSupport2 feedFormulationSupport2;

    @InjectMocks
    private FeedFormulationService2Impl feedFormulationService2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomFormulation() {
        // Arrange
        FeedFormulationRequest request = new FeedFormulationRequest();
        request.setFormulationName("Test Formulation");
        request.setProteins(Collections.singletonList(new FeedFormulationRequest.IngredientRequest("Soya Beans", 50.0)));
        request.setCarbohydrates(Collections.singletonList(new FeedFormulationRequest.IngredientRequest("Maize", 100.0)));

        when(feedFormulationSupport2.calculateTotalQuantityFromRequest(request)).thenReturn(150.0);
        when(feedFormulationSupport2.calculateTargetCpValue(request)).thenReturn(20.0);
        when(feedFormulationRepository2.existsByFormulationName(anyString())).thenReturn(false);

        FeedFormulation savedFormulation = FeedFormulation.builder()
                .formulationId(UUID.randomUUID().toString())
                .formulationName("Test Formulation")
                .date(LocalDate.now())
                .totalQuantityKg(150.0)
                .targetCpValue(20.0)
                .ingredient2s(Collections.emptyList())
                .build();

        when(feedFormulationRepository2.save(any(FeedFormulation.class))).thenReturn(savedFormulation);

        // Act
        FeedFormulation result = feedFormulationService2.createCustomFormulation(request);

        // Assert
        assertNotNull(result);
        assertEquals("Test Formulation", result.getFormulationName());
        assertEquals(150.0, result.getTotalQuantityKg());
        assertEquals(20.0, result.getTargetCpValue());
        verify(feedFormulationRepository2).save(any(FeedFormulation.class));
    }

    @Test
    void testGetCustomFormulationByIdAndDate() {
        // Arrange
        String formulationId = UUID.randomUUID().toString();
        LocalDate date = LocalDate.now();

        FeedFormulation formulation = FeedFormulation.builder()
                .formulationId(formulationId)
                .formulationName("Test Formulation")
                .date(date)
                .build();

        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any(LocalDate.class)))
                .thenReturn(Optional.of(formulation));

        // Act
        FeedFormulation result = feedFormulationService2.getCustomFormulationByIdAndDate(formulationId, date.toString());

        // Assert
        assertNotNull(result);
        assertEquals(formulationId, result.getFormulationId());
        assertEquals("Test Formulation", result.getFormulationName());
        verify(feedFormulationRepository2).findByFormulationIdAndDate(anyString(), any(LocalDate.class));
    }

    @Test
    void testGetCustomFormulations() {
        // Arrange
        when(feedFormulationRepository2.findAll()).thenReturn(Collections.emptyList());

        // Act
        var formulations = feedFormulationService2.getCustomFormulations();

        // Assert
        assertNotNull(formulations);
        assertEquals(0, formulations.size());
        verify(feedFormulationRepository2).findAll();
    }

    @Test
    void testUpdateCustomFeedFormulationByIdAndDate() {
        // Arrange
        String formulationId = UUID.randomUUID().toString();
        LocalDate date = LocalDate.now();

        FeedFormulationRequest request = new FeedFormulationRequest();
        request.setFormulationName("Updated Formulation");
        request.setProteins(Collections.singletonList(new FeedFormulationRequest.IngredientRequest("Groundnuts", 40.0)));
        request.setCarbohydrates(Collections.singletonList(new FeedFormulationRequest.IngredientRequest("Cassava", 60.0)));

        FeedFormulation existingFormulation = FeedFormulation.builder()
                .formulationId(formulationId)
                .formulationName("Original Formulation")
                .date(date)
                .build();

        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any(LocalDate.class)))
                .thenReturn(Optional.of(existingFormulation));
        when(feedFormulationSupport2.calculateTargetCpValue(request)).thenReturn(18.0);
        when(feedFormulationSupport2.calculateTotalQuantityFromRequest(request)).thenReturn(100.0);
        when(feedFormulationRepository2.save(any(FeedFormulation.class))).thenReturn(existingFormulation);

        // Act
        FeedFormulation result = feedFormulationService2.updateCustomFeedFormulationByIdAndDate(formulationId, date.toString(), request);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Formulation", result.getFormulationName());
        verify(feedFormulationRepository2).save(any(FeedFormulation.class));
    }

    @Test
    void testDeleteCustomFeedFormulationByIdAndDate() {
        // Arrange
        String formulationId = UUID.randomUUID().toString();
        LocalDate date = LocalDate.now();

        FeedFormulation formulation = FeedFormulation.builder()
                .formulationId(formulationId)
                .formulationName("Test Formulation")
                .date(date)
                .build();

        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any(LocalDate.class)))
                .thenReturn(Optional.of(formulation));
        doNothing().when(feedFormulationRepository2).delete(any(FeedFormulation.class));

        // Act
        feedFormulationService2.deleteCustomFeedFormulationByIdAndDate(formulationId, date.toString());

        // Assert
        verify(feedFormulationRepository2).delete(any(FeedFormulation.class));
    }
}
*/
