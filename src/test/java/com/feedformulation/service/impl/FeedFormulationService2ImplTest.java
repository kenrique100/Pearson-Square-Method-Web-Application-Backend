package com.feedformulation.service.impl;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.repository.FeedFormulationRepository2;
import com.feedformulation.utils.FeedFormulationSupport2;
import jakarta.validation.ValidationException;
import org.jetbrains.annotations.NotNull;
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

    private FeedFormulationRequest validRequest;
    private FeedFormulationRequest invalidRequest;
    private FeedFormulation savedFormulation;
    private String validFormulationId;
    private LocalDate validDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup valid and invalid requests for testing
        validRequest = getValidFeedFormulationRequest();
        invalidRequest = getInvalidFeedFormulationRequest();

        validFormulationId = UUID.randomUUID().toString();
        validDate = LocalDate.now();

        // Setup mock FeedFormulation for valid responses
        savedFormulation = FeedFormulation.builder()
                .formulationId(validFormulationId)
                .formulationName(validRequest.getFormulationName())
                .date(validDate)
                .totalQuantityKg(150.0)
                .targetCpValue(20.0)
                .ingredient2s(Collections.emptyList())
                .build();
    }

    // Helper methods for valid and invalid FeedFormulationRequest
    private static @NotNull FeedFormulationRequest getValidFeedFormulationRequest() {
        FeedFormulationRequest request = new FeedFormulationRequest();
        request.setFormulationName("Valid Formulation");
        request.setProteins(Collections.singletonList(new FeedFormulationRequest.IngredientRequest("Soya Beans", 50.0)));
        request.setCarbohydrates(Collections.singletonList(new FeedFormulationRequest.IngredientRequest("Maize", 100.0)));
        return request;
    }

    private static @NotNull FeedFormulationRequest getInvalidFeedFormulationRequest() {
        FeedFormulationRequest request = new FeedFormulationRequest();
        request.setFormulationName("");  // Invalid empty name
        request.setProteins(Collections.singletonList(new FeedFormulationRequest.IngredientRequest("Soya Beans", -50.0)));  // Invalid negative quantity
        return request;
    }

    // Test for creating a valid feed formulation
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

    // Test for creating a feed formulation with invalid request
    @Test
    void createCustomFormulation_invalidRequest_throwsException() {
        // Act & Assert
        assertThrows(ValidationException.class, () -> feedFormulationService2.createCustomFormulation(invalidRequest));
        verify(feedFormulationRepository2, times(0)).save(any(FeedFormulation.class));  // Ensure no save was attempted
    }

    // Test for retrieving a feed formulation by valid ID and date
    @Test
    void getCustomFormulationByIdAndDate_validIdAndDate_success() {
        // Arrange
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(savedFormulation));

        // Act
        FeedFormulation result = feedFormulationService2.getCustomFormulationByIdAndDate(validFormulationId, validDate.toString());

        // Assert
        assertNotNull(result);
        assertEquals(validFormulationId, result.getFormulationId());
        assertEquals("Valid Formulation", result.getFormulationName());
        verify(feedFormulationRepository2).findByFormulationIdAndDate(anyString(), any(LocalDate.class));
    }

    // Test for retrieving a feed formulation with invalid ID and date
    @Test
    void getCustomFormulationByIdAndDate_invalidIdAndDate_throwsException() {
        // Arrange
        when(feedFormulationRepository2.findByFormulationIdAndDate(anyString(), any(LocalDate.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FeedFormulationNotFoundException.class, () ->
                feedFormulationService2.getCustomFormulationByIdAndDate(UUID.randomUUID().toString(), LocalDate.now().toString()));
    }

    // Test for updating a valid feed formulation
    @Test
    void updateCustomFeedFormulationByIdAndDate_validRequest_success() {
        // Arrange
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(savedFormulation));
        when(feedFormulationRepository2.save(any(FeedFormulation.class))).thenReturn(savedFormulation);

        // Act
        FeedFormulation updatedResult = feedFormulationService2.updateCustomFeedFormulationByIdAndDate(validFormulationId, validDate.toString(), validRequest);

        // Assert
        assertNotNull(updatedResult);
        assertEquals(validRequest.getFormulationName(), updatedResult.getFormulationName());
        verify(feedFormulationRepository2).save(any(FeedFormulation.class));
    }

    // Test for updating with an invalid request
    @Test
    void updateCustomFeedFormulationByIdAndDate_invalidRequest_throwsException() {
        // Arrange
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(savedFormulation));

        // Act & Assert
        assertThrows(ValidationException.class, () ->
                feedFormulationService2.updateCustomFeedFormulationByIdAndDate(validFormulationId, validDate.toString(), invalidRequest));
        verify(feedFormulationRepository2, times(0)).save(any(FeedFormulation.class));  // Ensure no save was attempted
    }

    // Test for deleting a feed formulation by valid ID and date
    @Test
    void deleteCustomFeedFormulationByIdAndDate_validIdAndDate_success() {
        // Arrange
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(savedFormulation));

        // Act
        feedFormulationService2.deleteCustomFeedFormulationByIdAndDate(validFormulationId, validDate.toString());

        // Assert
        verify(feedFormulationRepository2).delete(any(FeedFormulation.class));
    }

    // Test for deleting a feed formulation with invalid ID and date
    @Test
    void deleteCustomFeedFormulationByIdAndDate_invalidIdAndDate_throwsException() {
        // Arrange
        when(feedFormulationRepository2.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FeedFormulationNotFoundException.class, () ->
                feedFormulationService2.deleteCustomFeedFormulationByIdAndDate(UUID.randomUUID().toString(), LocalDate.now().toString()));
        verify(feedFormulationRepository2, times(0)).delete(any(FeedFormulation.class));  // Ensure no delete was attempted
    }
}
