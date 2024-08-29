package com.api.feedFormulation.service;

import com.api.feedFormulation.dto.FeedRequestDTO;
import com.api.feedFormulation.dto.FeedResponseDTO;
import com.api.feedFormulation.exception.InvalidInputException;
import com.api.feedFormulation.model.FeedResponse;
import com.api.feedFormulation.model.Ingredient;
import com.api.feedFormulation.repository.FeedFormulationRepository;
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

    @InjectMocks
    private FeedFormulationServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateFeed_Success() {
        FeedRequestDTO request = FeedRequestDTO.builder()
                .quantity(100)
                .targetCpValue(20)
                .build();

        when(repository.save(any(FeedResponse.class))).thenAnswer(i -> i.getArguments()[0]);

        FeedResponseDTO response = service.calculateFeed(request);

        assertNotNull(response);
        assertEquals(100, response.getQuantity());
        assertEquals(20, response.getTargetCpValue());
        assertEquals(15, response.getIngredients().size());
    }

    @Test
    void calculateFeed_InvalidQuantity() {
        FeedRequestDTO request = FeedRequestDTO.builder()
                .quantity(-10)
                .targetCpValue(20)
                .build();

        assertThrows(InvalidInputException.class, () -> service.calculateFeed(request));
    }

    @Test
    void calculateFeed_InvalidCpValue() {
        FeedRequestDTO request = FeedRequestDTO.builder()
                .quantity(100)
                .targetCpValue(-5)
                .build();

        assertThrows(InvalidInputException.class, () -> service.calculateFeed(request));
    }

    @Test
    void getFeedResponseByFormulationIdAndDate_Success() {
        String formulationId = UUID.randomUUID().toString().substring(0, 5);
        String date = LocalDate.now().toString();

        FeedResponse feedResponse = FeedResponse.builder()
                .formulationId(formulationId)
                .date(date)
                .quantity(100)
                .targetCpValue(20)
                .ingredients(List.of(new Ingredient("Soya beans", 44.0, 30.0)))
                .build();

        when(repository.findByFormulationIdAndDate(formulationId, date)).thenReturn(Optional.of(feedResponse));

        FeedResponseDTO responseDTO = service.getFeedResponseByFormulationIdAndDate(formulationId, date);

        assertNotNull(responseDTO);
        assertEquals(formulationId, responseDTO.getFormulationId());
        assertEquals(date, responseDTO.getDate());
    }

    @Test
    void getFeedResponseByFormulationIdAndDate_NotFound() {
        String formulationId = "ABCDE";
        String date = LocalDate.now().toString();

        when(repository.findByFormulationIdAndDate(formulationId, date)).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> service.getFeedResponseByFormulationIdAndDate(formulationId, date));
    }

    @Test
    void getAllFeedFormulations_Success() {
        FeedResponse feedResponse = FeedResponse.builder()
                .formulationId("ABCDE")
                .date(LocalDate.now().toString())
                .quantity(100)
                .targetCpValue(20)
                .ingredients(List.of(new Ingredient("Soya beans", 44.0, 30.0)))
                .build();

        when(repository.findAll()).thenReturn(List.of(feedResponse));

        List<FeedResponseDTO> responses = service.getAllFeedFormulations();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void updateFeedResponse_Success() {
        String formulationId = UUID.randomUUID().toString().substring(0, 5);
        String date = LocalDate.now().toString();
        FeedRequestDTO request = FeedRequestDTO.builder()
                .quantity(200)
                .targetCpValue(25)
                .build();

        FeedResponse existingResponse = FeedResponse.builder()
                .formulationId(formulationId)
                .date(date)
                .quantity(100)
                .targetCpValue(20)
                .ingredients(List.of(new Ingredient("Soya beans", 44.0, 30.0)))
                .build();

        when(repository.findByFormulationIdAndDate(formulationId, date)).thenReturn(Optional.of(existingResponse));
        when(repository.save(any(FeedResponse.class))).thenAnswer(i -> i.getArguments()[0]);

        FeedResponseDTO updatedResponse = service.updateFeedResponse(formulationId, date, request);

        assertNotNull(updatedResponse);
        assertEquals(200, updatedResponse.getQuantity());
        assertEquals(25, updatedResponse.getTargetCpValue());
    }

    @Test
    void updateFeedResponse_NotFound() {
        String formulationId = "ABCDE";
        String date = LocalDate.now().toString();
        FeedRequestDTO request = FeedRequestDTO.builder()
                .quantity(200)
                .targetCpValue(25)
                .build();

        when(repository.findByFormulationIdAndDate(formulationId, date)).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> service.updateFeedResponse(formulationId, date, request));
    }

    @Test
    void deleteFeedResponse_Success() {
        String formulationId = UUID.randomUUID().toString().substring(0, 5);
        String date = LocalDate.now().toString();

        FeedResponse feedResponse = FeedResponse.builder()
                .formulationId(formulationId)
                .date(date)
                .quantity(100)
                .targetCpValue(20)
                .ingredients(List.of(new Ingredient("Soya beans", 44.0, 30.0)))
                .build();

        when(repository.findByFormulationIdAndDate(formulationId, date)).thenReturn(Optional.of(feedResponse));
        doNothing().when(repository).delete(any(FeedResponse.class));

        assertDoesNotThrow(() -> service.deleteFeedResponse(formulationId, date));
        verify(repository, times(1)).delete(feedResponse);
    }

    @Test
    void deleteFeedResponse_NotFound() {
        String formulationId = "ABCDE";
        String date = LocalDate.now().toString();

        when(repository.findByFormulationIdAndDate(formulationId, date)).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> service.deleteFeedResponse(formulationId, date));
    }
}
