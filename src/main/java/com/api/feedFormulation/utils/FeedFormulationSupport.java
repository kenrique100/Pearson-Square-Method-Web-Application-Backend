package com.api.feedFormulation.utils;

import com.api.feedFormulation.dto.FeedResponseDTO;
import com.api.feedFormulation.dto.IngredientDTO;
import com.api.feedFormulation.exception.InvalidInputException;
import com.api.feedFormulation.model.FeedResponse;
import com.api.feedFormulation.model.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FeedFormulationSupport {

    /**
     * Validates the feed request to ensure it contains valid data.
     *
     * @param quantity      Quantity of the feed.
     * @param targetCpValue Target crude protein value.
     */
    public void validateRequest(double quantity, double targetCpValue) {
        if (quantity <= 0 || quantity > 1000) {
            throw new InvalidInputException("Quantity must be greater than zero and not exceed 1000 kg.");
        }
        if (targetCpValue <= 0) {
            throw new InvalidInputException("Target CP value must be greater than zero.");
        }
    }

    /**
     * Generates a unique identifier for the feed formulation.
     *
     * @return A new UUID string.
     */
    public String generateGuid() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    /**
     * Creates a list of ingredients based on the specified quantity.
     *
     * @param quantity The quantity of the feed.
     * @return A list of ingredients.
     */
    public List<Ingredient> createIngredients(double quantity) {
        return List.of(
                Ingredient.builder().name("Soya beans").crudeProtein(Constants.CRUDE_SOYA_VALUE)
                        .quantity(quantity * Constants.CALC_003_VALUE).build(),
                Ingredient.builder().name("Groundnuts").crudeProtein(Constants.CRUDE_NUTS_VALUE)
                        .quantity(quantity * Constants.CALC_01_VALUE).build(),
                Ingredient.builder().name("Blood Meal").crudeProtein(Constants.CRUDE_BLOOD_VALUE)
                        .quantity(quantity * Constants.CALC_005_VALUE).build(),
                Ingredient.builder().name("Fish Meal").crudeProtein(Constants.CRUDE_FISH_VALUE)
                        .quantity(quantity * Constants.CALC_01_VALUE).build(),
                Ingredient.builder().name("Maize").crudeProtein(Constants.CRUDE_MAIZE_VALUE)
                        .quantity(quantity * Constants.CALC_02_VALUE).build(),
                Ingredient.builder().name("Cassava").crudeProtein(Constants.CRUDE_CAS_VALUE)
                        .quantity(quantity * Constants.CALC_01_VALUE).build(),
                Ingredient.builder().name("Diphosphate Calcium").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantity(quantity * Constants.CALC_002_VALUE).build(),
                Ingredient.builder().name("Bone Meal").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantity(quantity * Constants.CALC_002_VALUE).build(),
                Ingredient.builder().name("Marine Shell Flour").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantity(quantity * Constants.CALC_002_VALUE).build(),
                Ingredient.builder().name("Salt").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantity(quantity * Constants.CALC_0005_VALUE).build(),
                Ingredient.builder().name("Vitamin C").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantity(quantity * Constants.CALC_0005_VALUE).build(),
                Ingredient.builder().name("Premix").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantity(quantity * Constants.CALC_001_VALUE).build(),
                Ingredient.builder().name("Concentrate").crudeProtein(Constants.CRUDE_CON_VALUE)
                        .quantity(quantity * Constants.CALC_005_VALUE).build(),
                Ingredient.builder().name("Palm Oil").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantity(quantity * Constants.CALC_002_VALUE).build(),
                Ingredient.builder().name("Anti-toxin").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantity(quantity * Constants.CALC_00005_VALUE).build()
        );
    }

    /**
     * Maps a FeedResponse entity to its DTO representation.
     *
     * @param feedResponse The FeedResponse entity.
     * @return The corresponding FeedResponseDTO.
     */
    public FeedResponseDTO mapToDTO(FeedResponse feedResponse) {
        List<IngredientDTO> ingredientDTOs = feedResponse.getIngredients()
                .stream()
                .map(ingredient -> IngredientDTO.builder()
                        .name(ingredient.getName())
                        .crudeProtein(ingredient.getCrudeProtein())
                        .quantity(ingredient.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return FeedResponseDTO.builder()
                .formulationId(feedResponse.getFormulationId())
                .date(feedResponse.getDate())
                .quantity(feedResponse.getQuantity())
                .targetCpValue(feedResponse.getTargetCpValue())
                .ingredients(ingredientDTOs)
                .build();
    }

    /**
     * Sets the FeedResponse reference in each ingredient to ensure bidirectional consistency.
     *
     * @param feedResponse The FeedResponse entity.
     * @param ingredients  The list of ingredients.
     */
    public void setFeedResponseToIngredients(FeedResponse feedResponse, List<Ingredient> ingredients) {
        ingredients.forEach(ingredient -> ingredient.setFeedResponse(feedResponse));
    }
}
