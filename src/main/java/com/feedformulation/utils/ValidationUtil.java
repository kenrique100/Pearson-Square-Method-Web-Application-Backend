package com.feedformulation.utils;

import com.feedformulation.exception.InvalidInputException;

public class ValidationUtil {
    /**
     * Validates the input quantity and target CP value.
     *
     * @param quantity      the quantity to validate
     * @param targetCpValue the target CP value to validate
     * @throws InvalidInputException if the quantity or target CP value is invalid
     */
    public static void validateQuantityAndCpValue(double quantity, double targetCpValue) {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }

        if (targetCpValue <= 1 || targetCpValue > 100) {
            throw new InvalidInputException("Target CP value must be between 1 and 100.");
        }
    }
}
