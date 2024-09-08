package com.feedformulation.utils;

import com.feedformulation.dto.FeedFormulationRequest;
import com.feedformulation.dto.Ingredient2DTO;
import com.feedformulation.model.FeedFormulation;
import com.feedformulation.model.Ingredient2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedFormulationSupport2 {

    public double calculateTargetCpValue(FeedFormulationRequest request) {
        double totalQuantity = calculateTotalQuantity(request);

        double totalCrudeProtein = request.getIngredients().getProteins().stream()
                .mapToDouble(ingredient -> ingredient.getQuantityKg() * getCrudeProteinByIngredient(ingredient.getName()))
                .sum();

        totalCrudeProtein += request.getIngredients().getCarbohydrates().stream()
                .mapToDouble(ingredient -> ingredient.getQuantityKg() * getCrudeProteinByIngredient(ingredient.getName()))
                .sum();

        return round(totalCrudeProtein / totalQuantity);
    }

    public List<Ingredient2> createIngredients(FeedFormulationRequest request, FeedFormulation formulation) {
        List<Ingredient2> proteins = request.getIngredients().getProteins().stream()
                .map(ingredient -> Ingredient2.builder()
                        .name(ingredient.getName())
                        .crudeProtein(getCrudeProteinByIngredient(ingredient.getName()))
                        .quantityKg(ingredient.getQuantityKg())
                        .feedFormulation(formulation) // Associate with formulation
                        .build())
                .toList();

        List<Ingredient2> carbohydrates = request.getIngredients().getCarbohydrates().stream()
                .map(ingredient -> Ingredient2.builder()
                        .name(ingredient.getName())
                        .crudeProtein(getCrudeProteinByIngredient(ingredient.getName()))
                        .quantityKg(ingredient.getQuantityKg())
                        .feedFormulation(formulation) // Associate with formulation
                        .build())
                .toList();

        List<Ingredient2> allIngredient2s = new ArrayList<>(proteins);
        allIngredient2s.addAll(carbohydrates);

        allIngredient2s.addAll(createOtherIngredients(calculateTotalQuantity(request), formulation));

        return allIngredient2s;
    }

    public double calculateTotalQuantity(FeedFormulationRequest request) {
        double proteinQuantity = request.getIngredients().getProteins().stream()
                .mapToDouble(Ingredient2DTO::getQuantityKg).sum();

        double carbohydrateQuantity = request.getIngredients().getCarbohydrates().stream()
                .mapToDouble(Ingredient2DTO::getQuantityKg).sum();

        return proteinQuantity + carbohydrateQuantity;
    }

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

    private List<Ingredient2> createOtherIngredients(double totalQuantity, FeedFormulation formulation) {
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
                        .quantityKg(totalQuantity * Constants.CALC_002_VALUE).feedFormulation(formulation).build(),
                Ingredient2.builder().name("Anti-toxin").crudeProtein(Constants.CRUDE_00_VALUE)
                        .quantityKg(totalQuantity * Constants.CALC_00005_VALUE).feedFormulation(formulation).build()
        );
    }

    private double round(double value) {
        long factor = (long) Math.pow(10, 1);
        return (double) Math.round(value * factor) / factor;
    }
}
