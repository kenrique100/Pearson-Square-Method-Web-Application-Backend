/*
package com.feedformulation.service.impl;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.dto.FeedFormulationResponse;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.exception.InvalidInputException;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.model.Ingredient2;
import com.feedformulation.repository.FeedFormulationRepository2;
import com.feedformulation.utils.FeedFormulationSupport2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ValidationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedFormulationService2ImplTest {

    @Mock
    private FeedFormulationRepository2 feedFormulationRepository2;

    @Mock
    private FeedFormulationSupport2 feedFormulationSupport2;

    @InjectMocks
    private FeedFormulationService2Impl feedFormulationService2;

    private FeedFormulationRequest validRequest;
    private FeedFormulationRequest invalidRequest;

    @BeforeEach
    public void setUp() {
        validRequest = new FeedFormulationRequest();
        validRequest.setFormulationName("Test Formulation");

        // Create IngredientRequest instances using setters
        FeedFormulationRequest.IngredientRequest ingredient1 = new FeedFormulationRequest.IngredientRequest();
        ingredient1.setName("Soya Beans");
        ingredient1.setQuantityKg(10.0);

        FeedFormulationRequest.IngredientRequest ingredient2 = new FeedFormulationRequest.IngredientRequest();
        ingredient2.setName("Fish Meal");
        ingredient2.setQuantityKg(5.0);

        validRequest.setProteins(Arrays.asList(ingredient1, ingredient2));

        FeedFormulationRequest.IngredientRequest carbohydrate = new FeedFormulationRequest.IngredientRequest();
        carbohydrate.setName("Corn");
        carbohydrate.setQuantityKg(20.0);

        validRequest.setCarbohydrates(Arrays.asList(carbohydrate));

        invalidRequest = new FeedFormulationRequest();
        invalidRequest.setFormulationName("Invalid Formulation");
        invalidRequest.setProteins(Collections.emptyList());
        invalidRequest.setCarbohydrates(Collections.emptyList());
    }


    @Test
    public void createCustomFormulation_ValidRequest_ReturnsFormulation() {
        when(feedFormulationRepository2.existsByFormulationName(anyString())).thenReturn(false);
        when(feedFormulationSupport2.calculateTotalQuantityFromRequest(any())).thenReturn(35.0);
        when(feedFormulationSupport2.calculateTargetCpValue(any())).thenReturn(20.0);
        when(feedFormulationRepository2.save(any(FeedFormulation.class))).thenReturn(new FeedFormulation());

        FeedFormulation result = feedFormulationService2.createCustomFormulation(validRequest);

        assertNotNull(result);
        verify(feedFormulationRepository2).save(any(FeedFormulation.class));
    }

    @Test
    public void createCustomFormulation_DuplicateFormulationName_ThrowsInvalidInputException() {
        when(feedFormulationRepository2.existsByFormulationName(anyString())).thenReturn(true);

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            feedFormulationService2.createCustomFormulation(validRequest);
        });

        assertEquals("Formulation name must be unique.", exception.getMessage());
    }

    @Test
    public void createCustomFormulation_InvalidRequest_ThrowsValidationException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            feedFormulationService2.createCustomFormulation(invalidRequest);
        });

        assertEquals("Proteins are required.", exception.getMessage());
    }

    @Test
    public void getCustomFormulationByIdAndDate_ValidIdAndDate_ReturnsFormulation() {
        String id = "123";
        LocalDate date = LocalDate.now();
        FeedFormulation formulation = new FeedFormulation();
        formulation.setFormulationId(id);
        formulation.setDate(date);

        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any())).thenReturn(Optional.of(formulation));

        FeedFormulation result = feedFormulationService2.getCustomFormulationByIdAndDate(id, date.toString());

        assertEquals(id, result.getFormulationId());
        assertEquals(date, result.getDate());
    }

    @Test
    public void getCustomFormulationByIdAndDate_NotFound_ThrowsFeedFormulationNotFoundException() {
        String id = "123";
        LocalDate date = LocalDate.now();

        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any())).thenReturn(Optional.empty());

        FeedFormulationNotFoundException exception = assertThrows(FeedFormulationNotFoundException.class, () -> {
            feedFormulationService2.getCustomFormulationByIdAndDate(id, date.toString());
        });

        assertEquals("Feed formulation not found for ID: " + id + " and Date: " + date, exception.getMessage());
    }

    @Test
    public void updateCustomFeedFormulationByIdAndDate_ValidRequest_ReturnsUpdatedFormulation() {
        // Setup test data
        String id = "123";
        LocalDate date = LocalDate.now();
        FeedFormulation existingFormulation = new FeedFormulation();
        existingFormulation.setFormulationId(id);
        existingFormulation.setDate(date);

        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any())).thenReturn(Optional.of(existingFormulation));
        when(feedFormulationSupport2.calculateTotalQuantityFromRequest(any())).thenReturn(35.0);
        when(feedFormulationSupport2.calculateTargetCpValue(any())).thenReturn(20.0);
        when(feedFormulationRepository2.save(any(FeedFormulation.class))).thenReturn(existingFormulation);

        FeedFormulation result = feedFormulationService2.updateCustomFeedFormulationByIdAndDate(id, date.toString(), validRequest);

        assertEquals(id, result.getFormulationId());
        verify(feedFormulationRepository2).save(any(FeedFormulation.class));
    }

    @Test
    public void deleteCustomFeedFormulationByIdAndDate_ValidIdAndDate_DeletesFormulation() {
        String id = "123";
        LocalDate date = LocalDate.now();
        FeedFormulation existingFormulation = new FeedFormulation();
        existingFormulation.setFormulationId(id);
        existingFormulation.setDate(date);

        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any())).thenReturn(Optional.of(existingFormulation));

        feedFormulationService2.deleteCustomFeedFormulationByIdAndDate(id, date.toString());

        verify(feedFormulationRepository2).delete(existingFormulation);
    }

    @Test
    public void deleteCustomFeedFormulationByIdAndDate_NotFound_ThrowsFeedFormulationNotFoundException() {
        String id = "123";
        LocalDate date = LocalDate.now();

        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any())).thenReturn(Optional.empty());

        FeedFormulationNotFoundException exception = assertThrows(FeedFormulationNotFoundException.class, () -> {
            feedFormulationService2.deleteCustomFeedFormulationByIdAndDate(id, date.toString());
        });

        assertEquals("Feed formulation not found for ID: " + id + " and Date: " + date, exception.getMessage());
    }
}
*/
