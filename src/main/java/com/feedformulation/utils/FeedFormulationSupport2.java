package com.feedformulation.utils;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.model.Ingredient2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedFormulationSupport2 {

    /**
     * Creates a list of Ingredient2 objects for the feed formulation,
     * mapping them from the provided FeedFormulationRequest.
     *
     * @param request The request containing ingredient data.
     * @param formulation The associated FeedFormulation object.
     * @return A list of Ingredient2 objects created from the request.
     */
    public List<Ingredient2> createIngredients(FeedFormulationRequest request, FeedFormulation formulation) {
        List<Ingredient2> proteins = request.getProteins().stream()
                .map(ingredient -> Ingredient2.builder()
                        .name(ingredient.getName())
                        .crudeProtein(getCrudeProteinByIngredient(ingredient.getName()))
                        .quantityKg(ingredient.getQuantityKg())
                        .feedFormulation(formulation)
                        .build())
                .toList();

        List<Ingredient2> carbohydrates = request.getCarbohydrates().stream()
                .map(ingredient -> Ingredient2.builder()
                        .name(ingredient.getName())
                        .crudeProtein(getCrudeProteinByIngredient(ingredient.getName()))
                        .quantityKg(ingredient.getQuantityKg())
                        .feedFormulation(formulation)
                        .build())
                .toList();

        List<Ingredient2> mainIngredients = new ArrayList<>(proteins);
        mainIngredients.addAll(carbohydrates);

        return mainIngredients;
    }

    /**
     * Creates a list of other ingredients based on the total quantity.
     * These are usually additives or essential components.
     *
     * @param totalQuantity The total quantity of the feed formulation.
     * @param formulation The associated FeedFormulation object.
     * @return A list of Ingredient2 objects for the additional ingredients.
     */
    public List<Ingredient2> createOtherIngredients(double totalQuantity, FeedFormulation formulation) {
        return List.of(
                Ingredient2.builder().name("DiPhosphate Calcium").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg(totalQuantity * Constants.CALC_002_VALUE).feedFormulation(formulation).build(),
                Ingredient2.builder().name("Bone Meal").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg(totalQuantity * Constants.CALC_002_VALUE).feedFormulation(formulation).build(),
                Ingredient2.builder().name("Marine Shell Flour").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg(totalQuantity * Constants.CALC_002_VALUE).feedFormulation(formulation).build(),
                Ingredient2.builder().name("Salt").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg(totalQuantity * Constants.CALC_0005_VALUE).feedFormulation(formulation).build(),
                Ingredient2.builder().name("Vitamin C").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg(totalQuantity * Constants.CALC_0005_VALUE).feedFormulation(formulation).build(),
                Ingredient2.builder().name("Premix").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg(totalQuantity * Constants.CALC_001_VALUE).feedFormulation(formulation).build(),
                Ingredient2.builder().name("Palm Oil").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg((totalQuantity * Constants.CALC_002_VALUE) / 0.92)  // Convert from kg to liters
                        .build(),
                Ingredient2.builder().name("Anti-toxin").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg(totalQuantity * Constants.CALC_00005_VALUE).feedFormulation(formulation).build()
        );
    }

    /**
     * Calculates the total quantity of ingredients from a list of ingredient requests.
     *
     * @param ingredients The list of ingredient requests.
     * @return The total quantity of all ingredients in kilograms.
     */
    public double calculateTotalQuantity(List<FeedFormulationRequest.IngredientRequest> ingredients) {
        return ingredients.stream()
                .mapToDouble(FeedFormulationRequest.IngredientRequest::getQuantityKg)
                .sum();
    }

    /**
     * Calculates the target crude protein value for the formulation based on its ingredients.
     *
     * @param request The request containing ingredient data.
     * @return The calculated target crude protein value.
     */
    public double calculateTargetCpValue(FeedFormulationRequest request) {
        double totalProteinsQuantity = calculateTotalQuantity(request.getProteins());
        double totalCarbohydratesQuantity = calculateTotalQuantity(request.getCarbohydrates());
        double totalQuantity = totalProteinsQuantity + totalCarbohydratesQuantity;

        double totalCrudeProtein = request.getProteins().stream()
                .mapToDouble(ingredient -> ingredient.getQuantityKg() * getCrudeProteinByIngredient(ingredient.getName()))
                .sum();
        totalCrudeProtein += request.getCarbohydrates().stream()
                .mapToDouble(ingredient -> ingredient.getQuantityKg() * getCrudeProteinByIngredient(ingredient.getName()))
                .sum();

        return totalQuantity > 0 ? round(totalCrudeProtein / totalQuantity) : 0.0;
    }

    /**
     * Returns the crude protein content for a specific ingredient based on its name.
     *
     * @param ingredientName The name of the ingredient.
     * @return The crude protein value for the ingredient.
     */
    private double getCrudeProteinByIngredient(String ingredientName) {
        return switch (ingredientName.toLowerCase()) {
            case "soya beans" -> Constants.CRUDE_SOYA_VALUE;
            case "groundnuts" -> Constants.CRUDE_NUTS_VALUE;
            case "blood meal" -> Constants.CRUDE_BLOOD_VALUE;
            case "fish meal" -> Constants.CRUDE_FISH_VALUE;
            case "maize" -> Constants.CRUDE_MAIZE_VALUE;
            case "cassava" -> Constants.CRUDE_CAS_VALUE;
            default -> Constants.CRUDE_00_VALUE;
        };
    }

    /**
     * Rounds a given value to one decimal place.
     *
     * @param value The value to be rounded.
     * @return The rounded value.
     */
    private double round(double value) {
        long factor = (long) Math.pow(10, 1);
        return (double) Math.round(value * factor) / factor;
    }

    /**
     * Calculates the total quantity of ingredients from the request,
     * considering both protein and carbohydrate ingredients.
     *
     * @param request The request containing ingredient data.
     * @return The total quantity of all ingredients in kilograms.
     */
    public double calculateTotalQuantityFromRequest(FeedFormulationRequest request) {
        double totalProteinsQuantity = calculateTotalQuantity(request.getProteins());
        double totalCarbohydratesQuantity = calculateTotalQuantity(request.getCarbohydrates());
        return totalProteinsQuantity + totalCarbohydratesQuantity;
    }
}
