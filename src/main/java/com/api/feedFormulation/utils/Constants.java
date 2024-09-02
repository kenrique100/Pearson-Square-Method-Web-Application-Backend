package com.api.feedFormulation.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constants {

    // constants for ingredients
    public static final String PROTEINS = "Proteins";
    public static final String CARBOHYDRATES = "Carbohydrates";
    public static final String MINERALS = "Minerals";
    public static final String VITAMINS = "Vitamins";
    public static final String OTHERS = "Others";

    // Calculation values
    public static double CALC_003_VALUE;
    public static double CALC_01_VALUE;
    public static double CALC_005_VALUE;
    public static double CALC_02_VALUE;
    public static double CALC_002_VALUE;
    public static double CALC_0005_VALUE;
    public static double CALC_001_VALUE;
    public static double CALC_00005_VALUE;

    // Crude protein values
    public static double CRUDE_00_VALUE;
    public static double CRUDE_SOYA_VALUE;
    public static double CRUDE_NUTS_VALUE;
    public static double CRUDE_BLOOD_VALUE;
    public static double CRUDE_FISH_VALUE;
    public static double CRUDE_MAIZE_VALUE;
    public static double CRUDE_CAS_VALUE;
    public static double CRUDE_CON_VALUE;

    @Value("${feedFormulation.calc003Value}")
    public void setCalc003Value(double calc003Value) {
        CALC_003_VALUE = calc003Value;
    }

    @Value("${feedFormulation.calc01Value}")
    public void setCalc01Value(double calc01Value) {
        CALC_01_VALUE = calc01Value;
    }

    @Value("${feedFormulation.calc005Value}")
    public void setCalc005Value(double calc005Value) {
        CALC_005_VALUE = calc005Value;
    }

    @Value("${feedFormulation.calc02Value}")
    public void setCalc02Value(double calc02Value) {
        CALC_02_VALUE = calc02Value;
    }

    @Value("${feedFormulation.calc002Value}")
    public void setCalc002Value(double calc002Value) {
        CALC_002_VALUE = calc002Value;
    }

    @Value("${feedFormulation.calc0005Value}")
    public void setCalc0005Value(double calc0005Value) {
        CALC_0005_VALUE = calc0005Value;
    }

    @Value("${feedFormulation.calc001Value}")
    public void setCalc001Value(double calc001Value) {
        CALC_001_VALUE = calc001Value;
    }

    @Value("${feedFormulation.calc00005Value}")
    public void setCalc00005Value(double calc00005Value) {
        CALC_00005_VALUE = calc00005Value;
    }

    @Value("${feedFormulation.crude00Value}")
    public void setCrude00Value(double crude00Value) {
        CRUDE_00_VALUE = crude00Value;
    }

    @Value("${feedFormulation.crudeSoyaValue}")
    public void setCrudeSoyaValue(double crudeSoyaValue) {
        CRUDE_SOYA_VALUE = crudeSoyaValue;
    }

    @Value("${feedFormulation.crudeNutsValue}")
    public void setCrudeNutsValue(double crudeNutsValue) {
        CRUDE_NUTS_VALUE = crudeNutsValue;
    }

    @Value("${feedFormulation.crudeBloodValue}")
    public void setCrudeBloodValue(double crudeBloodValue) {
        CRUDE_BLOOD_VALUE = crudeBloodValue;
    }

    @Value("${feedFormulation.crudeFishValue}")
    public void setCrudeFishValue(double crudeFishValue) {
        CRUDE_FISH_VALUE = crudeFishValue;
    }

    @Value("${feedFormulation.crudeMaizeValue}")
    public void setCrudeMaizeValue(double crudeMaizeValue) {
        CRUDE_MAIZE_VALUE = crudeMaizeValue;
    }

    @Value("${feedFormulation.crudeCasValue}")
    public void setCrudeCasValue(double crudeCasValue) {
        CRUDE_CAS_VALUE = crudeCasValue;
    }

    @Value("${feedFormulation.crudeConValue}")
    public void setCrudeConValue(double crudeConValue) {
        CRUDE_CON_VALUE = crudeConValue;
    }
}
