package com.feedformulation.service.impl;

import com.feedformulation.dto.FeedRequestDTO;
import com.feedformulation.dto.FeedResponseDTO;
import com.feedformulation.exception.FeedFormulationNotFoundException;
import com.feedformulation.exception.InvalidInputException;
import com.feedformulation.model.FeedResponse;
import com.feedformulation.model.Ingredient;
import com.feedformulation.repository.FeedFormulationRepository;
import com.feedformulation.utils.FeedFormulationSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FeedFormulationServiceImplTest {

    @Mock
    private FeedFormulationRepository repository;

    @Mock
    private FeedFormulationSupport support;

    @InjectMocks
    private FeedFormulationServiceImpl service;

    private FeedRequestDTO validRequest;
    private FeedRequestDTO invalidRequest;
    private FeedResponse validResponse;
    private final String validFormulationId = UUID.randomUUID().toString().substring(0, 5);
    private final String validDate = LocalDate.now().toString();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks

        // Setup valid request and response objects for tests
        validRequest = FeedRequestDTO.builder()
                .formulationName("Test Formulation")
                .quantity(1000)
                .targetCpValue(25.0)
                .build();

        invalidRequest = FeedRequestDTO.builder()
                .formulationName("")  // Invalid empty name
                .quantity(-500)        // Invalid negative quantity
                .targetCpValue(0)
                .build();

        validResponse = FeedResponse.builder()
                .formulationId(validFormulationId)
                .formulationName(validRequest.getFormulationName())
                .quantity(validRequest.getQuantity())
                .targetCpValue(validRequest.getTargetCpValue())
                .date(validDate)
                .ingredients(List.of(
                        Ingredient.builder().name("Soya beans").crudeProtein(35).quantity(500).build(),
                        Ingredient.builder().name("Fish Meal").crudeProtein(65).quantity(500).build()
                ))
                .build();
    }

    // Test for creating a valid feed formulation
    @Test
    void createFeedFormulation_validRequest_success() {
        // Arrange
        when(repository.existsByFormulationName(validRequest.getFormulationName())).thenReturn(false);
        when(support.createIngredients(validRequest.getQuantity())).thenReturn(validResponse.getIngredients());
        when(support.generateGuid()).thenReturn(validFormulationId);
        when(repository.save(any(FeedResponse.class))).thenReturn(validResponse);
        when(support.mapToDTO(any(FeedResponse.class))).thenReturn(FeedResponseDTO.builder()
                .formulationId(validResponse.getFormulationId())
                .formulationName(validResponse.getFormulationName())
                .quantity(validResponse.getQuantity())
                .targetCpValue(validResponse.getTargetCpValue())
                .ingredients(List.of())  // Simplified ingredients for test
                .build());

        // Act
        FeedResponseDTO responseDTO = service.createFeedFormulation(validRequest);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(validRequest.getFormulationName(), responseDTO.getFormulationName());
        verify(repository, times(1)).save(any(FeedResponse.class));
    }

    // Test for creating a feed formulation with an invalid request
    @Test
    void createFeedFormulation_invalidRequest_throwsException() {
        assertThrows(InvalidInputException.class, () -> service.createFeedFormulation(invalidRequest));
    }

    // Test for retrieving a formulation by valid ID and date
    @Test
    void getFormulationByIdAndDate_validIdAndDate_success() {
        // Arrange
        when(repository.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(validResponse));
        when(support.mapToDTO(validResponse)).thenReturn(FeedResponseDTO.builder()
                .formulationId(validResponse.getFormulationId())
                .formulationName(validResponse.getFormulationName())
                .quantity(validResponse.getQuantity())
                .targetCpValue(validResponse.getTargetCpValue())
                .ingredients(List.of())  // Simplified ingredients
                .build());

        // Act
        FeedResponseDTO responseDTO = service.getFormulationByIdAndDate(validFormulationId, validDate);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(validFormulationId, responseDTO.getFormulationId());
        assertEquals(validDate, validResponse.getDate());
        verify(repository, times(1)).findByFormulationIdAndDate(validFormulationId, validDate);
    }

    // Test for retrieving a formulation by invalid ID and date
    @Test
    void getFormulationByIdAndDate_invalidIdAndDate_throwsException() {
        // Arrange
        when(repository.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FeedFormulationNotFoundException.class, () -> service.getFormulationByIdAndDate(validFormulationId, validDate));
    }

    // Test for updating a valid feed formulation
    @Test
    void updateFeedFormulationByIdAndDate_validRequest_success() {
        // Arrange
        when(repository.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(validResponse));
        when(repository.save(any(FeedResponse.class))).thenReturn(validResponse);
        when(support.mapToDTO(validResponse)).thenReturn(FeedResponseDTO.builder()
                .formulationId(validFormulationId)
                .formulationName(validRequest.getFormulationName())
                .quantity(validRequest.getQuantity())
                .targetCpValue(validRequest.getTargetCpValue())
                .ingredients(List.of())  // Simplified ingredients
                .build());

        // Act
        FeedResponseDTO updatedResponse = service.updateFeedFormulationByIdAndDate(validFormulationId, validDate, validRequest);

        // Assert
        assertNotNull(updatedResponse);
        assertEquals(validRequest.getFormulationName(), updatedResponse.getFormulationName());
        verify(repository, times(1)).save(any(FeedResponse.class));
    }

    // Test for updating with invalid request
    @Test
    void updateFeedFormulationByIdAndDate_invalidRequest_throwsException() {
        FeedRequestDTO invalidRequest = FeedRequestDTO.builder()
                .formulationName("Invalid Formulation")
                .quantity(0)  // Invalid quantity
                .targetCpValue(0)
                .build();

        when(repository.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(validResponse));

        assertThrows(InvalidInputException.class, () -> service.updateFeedFormulationByIdAndDate(validFormulationId, validDate, invalidRequest));
        verify(repository, times(0)).save(any(FeedResponse.class));
    }

    // Test for deleting a formulation by valid ID and date
    @Test
    void deleteFeedFormulationByIdAndDate_validIdAndDate_success() {
        when(repository.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.of(validResponse));

        service.deleteFeedFormulationByIdAndDate(validFormulationId, validDate);

        verify(repository, times(1)).delete(any(FeedResponse.class));
    }

    // Test for deleting a formulation by invalid ID and date
    @Test
    void deleteFeedFormulationByIdAndDate_invalidIdAndDate_throwsException() {
        when(repository.findByFormulationIdAndDate(validFormulationId, validDate)).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> service.deleteFeedFormulationByIdAndDate(validFormulationId, validDate));
    }
}
