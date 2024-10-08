package com.feedformulation.service.impl;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.exception.InvalidInputException;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.model.Ingredient2;
import com.feedformulation.repository.FeedFormulationRepository2;
import com.feedformulation.utils.FeedFormulationSupport2;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedFormulationService2ImplTest {

    @Mock
    private FeedFormulationRepository2 feedFormulationRepository2;

    @Mock
    private FeedFormulationSupport2 feedFormulationSupport2;

    @InjectMocks
    private FeedFormulationService2Impl feedFormulationService2;

    private FeedFormulationRequest validRequest;
    private FeedFormulationRequest invalidRequest;
    private FeedFormulation savedFormulation;
    private String validFormulationId;
    private LocalDate validDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validFormulationId = UUID.randomUUID().toString();
        validDate = LocalDate.now();

        validRequest = new FeedFormulationRequest(
                "Valid Formulation",
                List.of(new FeedFormulationRequest.IngredientRequest("Soya Beans", 50.0)),
                List.of(new FeedFormulationRequest.IngredientRequest("Maize", 100.0))
        );

        invalidRequest = new FeedFormulationRequest(
                "",
                List.of(new FeedFormulationRequest.IngredientRequest("Soya Beans", -50.0)),
                List.of(new FeedFormulationRequest.IngredientRequest("Maize", 100.0))
        );

        savedFormulation = FeedFormulation.builder()
                .formulationId(validFormulationId)
                .formulationName(validRequest.getFormulationName())
                .date(validDate)
                .totalQuantityKg(150.0)
                .targetCpValue(20.0)
                .ingredient2s(Collections.emptyList())
                .build();
    }

    @Test
    void createCustomFormulation_validRequest_success() {
        // Arrange
        when(feedFormulationSupport2.calculateTotalQuantityFromRequest(validRequest)).thenReturn(150.0);
        when(feedFormulationSupport2.calculateTargetCpValue(validRequest)).thenReturn(20.0);
        when(feedFormulationRepository2.existsByFormulationName(validRequest.getFormulationName())).thenReturn(false);
        when(feedFormulationRepository2.save(any(FeedFormulation.class))).thenReturn(savedFormulation);

        // Act
        FeedFormulation result = feedFormulationService2.createCustomFormulation(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals(validRequest.getFormulationName(), result.getFormulationName());
        assertEquals(150.0, result.getTotalQuantityKg());
        assertEquals(20.0, result.getTargetCpValue());
        verify(feedFormulationRepository2).save(any(FeedFormulation.class));
    }
    // Helper method to create mock ingredients
    private @NotNull List<Ingredient2> createMockIngredients(FeedFormulation savedFormulation) {
        // Use the correct constructor with appropriate arguments (Long, String, double, double, FeedFormulation)
        Ingredient2 ingredient1 = new Ingredient2(1L, "Soya Beans", 100.0, 40.0, savedFormulation);
        Ingredient2 ingredient2 = new Ingredient2(2L, "Maize", 50.0, 8.0, savedFormulation);
        return Arrays.asList(ingredient1, ingredient2);
    }


    @Test
    void createCustomFormulation_invalidRequest_shouldThrowInvalidInputException() {
        assertThrows(InvalidInputException.class, () ->
                feedFormulationService2.createCustomFormulation(invalidRequest)
        );

        verify(feedFormulationRepository2, never()).save(any());
    }

    @Test
    void getCustomFormulationByIdAndDate_validIdAndDate_shouldReturnFormulation() {
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(savedFormulation));

        FeedFormulation result = feedFormulationService2.getCustomFormulationByIdAndDate(validFormulationId, validDate.toString());

        assertNotNull(result);
        assertEquals(validFormulationId, result.getFormulationId());
        assertEquals(validDate, result.getDate());

        verify(feedFormulationRepository2).findByFormulationIdAndDate(validFormulationId, validDate);
    }

    @Test
    void getCustomFormulationByIdAndDate_invalidIdOrDate_shouldThrowNotFoundException() {
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.empty());

        assertThrows(FeedFormulationNotFoundException.class, () ->
                feedFormulationService2.getCustomFormulationByIdAndDate(validFormulationId, validDate.toString()));

        verify(feedFormulationRepository2).findByFormulationIdAndDate(validFormulationId, validDate);
    }

    @Test
    void updateCustomFeedFormulationByIdAndDate_validIdAndRequest_shouldUpdateFormulation() {
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(savedFormulation));
        when(feedFormulationSupport2.calculateTotalQuantityFromRequest(validRequest)).thenReturn(150.0);
        when(feedFormulationSupport2.calculateTargetCpValue(validRequest)).thenReturn(20.0);
        when(feedFormulationSupport2.createIngredients(validRequest, savedFormulation)).thenReturn(Collections.emptyList());
        when(feedFormulationSupport2.createOtherIngredients(150.0, savedFormulation)).thenReturn(Collections.emptyList());
        when(feedFormulationRepository2.save(any(FeedFormulation.class))).thenReturn(savedFormulation);

        FeedFormulation result = feedFormulationService2.updateCustomFeedFormulationByIdAndDate(validFormulationId, validDate.toString(), validRequest);

        assertNotNull(result);
        assertEquals(validFormulationId, result.getFormulationId());
        assertEquals(150.0, result.getTotalQuantityKg());
        assertEquals(20.0, result.getTargetCpValue());

        verify(feedFormulationRepository2).save(any(FeedFormulation.class));
    }

    @Test
    void deleteCustomFeedFormulationByIdAndDate_validIdAndDate_shouldDeleteFormulation() {
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(savedFormulation));

        feedFormulationService2.deleteCustomFeedFormulationByIdAndDate(validFormulationId, validDate.toString());

        verify(feedFormulationRepository2).delete(savedFormulation);
    }

    @Test
    void deleteCustomFeedFormulationByIdAndDate_invalidIdOrDate_shouldThrowNotFoundException() {
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.empty());

        assertThrows(FeedFormulationNotFoundException.class, () ->
                feedFormulationService2.deleteCustomFeedFormulationByIdAndDate(validFormulationId, validDate.toString()));

        verify(feedFormulationRepository2, never()).delete(any());
    }
}
