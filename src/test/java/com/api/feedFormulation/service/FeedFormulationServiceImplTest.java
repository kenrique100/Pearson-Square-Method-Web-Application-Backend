/*
package com.api.feedFormulation.service;

import com.api.feedFormulation.dto.FeedRequestDTO;
import com.api.feedFormulation.dto.FeedResponseDTO;
import com.api.feedFormulation.exception.InvalidInputException;
import com.api.feedFormulation.model.FeedResponse;
import com.api.feedFormulation.repository.FeedFormulationRepository;
import com.api.feedFormulation.utils.FeedFormulationSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class FeedFormulationServiceImplTest {

    @Mock
    private FeedFormulationRepository repository;

    @Mock
    private FeedFormulationSupport support;

    @InjectMocks
    private FeedFormulationServiceImpl service;

    private FeedRequestDTO request;
    private FeedResponse feedResponse;
    private FeedResponseDTO responseDTO;

    @BeforeEach
    public void setUp() {
        // Initialize FeedRequestDTO with appropriate constructor
        request = new FeedRequestDTO(100, 30);

        // Initialize FeedResponse using builder pattern
        feedResponse = FeedResponse.builder()
                .formulationId(UUID.randomUUID().toString())
                .date(LocalDate.now().toString())
                .quantity(100)
                .targetCpValue(30)
                .build();

        // Initialize FeedResponseDTO with appropriate constructor
        responseDTO = new FeedResponseDTO(
                feedResponse.getFormulationId(),
                feedResponse.getDate(),
                feedResponse.getQuantity(),
                feedResponse.getTargetCpValue(),
                Collections.emptyList()
        );
    }

    @Test
    @Transactional
    public void testCalculateFeed() {
        Mockito.doNothing().when(support).validateRequest(any(Double.class), any(Double.class));
        Mockito.when(support.createIngredients(any(Double.class))).thenReturn(Collections.emptyList());
        Mockito.when(support.generateGuid()).thenReturn(feedResponse.getFormulationId());
        Mockito.when(repository.save(any(FeedResponse.class))).thenReturn(feedResponse);
        Mockito.when(support.mapToDTO(any(FeedResponse.class))).thenReturn(responseDTO);

        FeedResponseDTO result = service.calculateFeed(request);

        assertNotNull(result);
        assertEquals(feedResponse.getFormulationId(), result.getFormulationId());
        assertEquals(feedResponse.getDate(), result.getDate());
        assertEquals(feedResponse.getQuantity(), result.getQuantity());
        assertEquals(feedResponse.getTargetCpValue(), result.getTargetCpValue());
    }

    @Test
    public void testGetFeedResponseByFormulationIdAndDate() {
        Mockito.when(repository.findByFormulationIdAndDate(anyString(), anyString())).thenReturn(Optional.of(feedResponse));
        Mockito.when(support.mapToDTO(any(FeedResponse.class))).thenReturn(responseDTO);

        FeedResponseDTO result = service.getFeedResponseByFormulationIdAndDate(feedResponse.getFormulationId(), feedResponse.getDate());

        assertNotNull(result);
        assertEquals(feedResponse.getFormulationId(), result.getFormulationId());
        assertEquals(feedResponse.getDate(), result.getDate());
        assertEquals(feedResponse.getQuantity(), result.getQuantity());
        assertEquals(feedResponse.getTargetCpValue(), result.getTargetCpValue());
    }

    @Test
    public void testGetFeedResponseByFormulationIdAndDateNotFound() {
        Mockito.when(repository.findByFormulationIdAndDate(anyString(), anyString())).thenReturn(Optional.empty());

        InvalidInputException thrown = assertThrows(InvalidInputException.class, () ->
                service.getFeedResponseByFormulationIdAndDate(feedResponse.getFormulationId(), feedResponse.getDate()));

        assertEquals("Feed formulation not found", thrown.getMessage());
    }

    @Test
    public void testGetAllFeedFormulations() {
        Mockito.when(repository.findAll()).thenReturn(List.of(feedResponse));
        Mockito.when(support.mapToDTO(any(FeedResponse.class))).thenReturn(responseDTO);

        List<FeedResponseDTO> result = service.getAllFeedFormulations();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(feedResponse.getFormulationId(), result.get(0).getFormulationId());
    }

    @Test
    public void testUpdateFeedResponse() {
        Mockito.when(repository.findByFormulationIdAndDate(anyString(), anyString())).thenReturn(Optional.of(feedResponse));
        Mockito.when(repository.save(any(FeedResponse.class))).thenReturn(feedResponse);
        Mockito.when(support.mapToDTO(any(FeedResponse.class))).thenReturn(responseDTO);

        FeedResponseDTO result = service.updateFeedResponse(feedResponse.getFormulationId(), feedResponse.getDate(), request);

        assertNotNull(result);
        assertEquals(feedResponse.getFormulationId(), result.getFormulationId());
        assertEquals(request.getQuantity(), result.getQuantity());
        assertEquals(request.getTargetCpValue(), result.getTargetCpValue());
    }

    @Test
    public void testUpdateFeedResponseNotFound() {
        Mockito.when(repository.findByFormulationIdAndDate(anyString(), anyString())).thenReturn(Optional.empty());

        InvalidInputException thrown = assertThrows(InvalidInputException.class, () ->
                service.updateFeedResponse(feedResponse.getFormulationId(), feedResponse.getDate(), request));

        assertEquals("Feed formulation not found", thrown.getMessage());
    }

    @Test
    public void testDeleteFeedResponse() {
        Mockito.when(repository.findByFormulationIdAndDate(anyString(), anyString())).thenReturn(Optional.of(feedResponse));
        Mockito.doNothing().when(repository).delete(any(FeedResponse.class));

        assertDoesNotThrow(() -> service.deleteFeedResponse(feedResponse.getFormulationId(), feedResponse.getDate()));
    }

    @Test
    public void testDeleteFeedResponseNotFound() {
        Mockito.when(repository.findByFormulationIdAndDate(anyString(), anyString())).thenReturn(Optional.empty());

        InvalidInputException thrown = assertThrows(InvalidInputException.class, () ->
                service.deleteFeedResponse(feedResponse.getFormulationId(), feedResponse.getDate()));

        assertEquals("Feed formulation not found", thrown.getMessage());
    }
}
*/
