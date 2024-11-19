package com.citronix.api.Helpers;

import org.springframework.stereotype.Component;

@Component
public class FieldValidator {

    public void validateFieldCount(Long numberOfFieldsByFarm) {
        if (numberOfFieldsByFarm >= 10) {
            throw new IllegalStateException("A farm can only have up to 10 fields.");
        }
    }

    public void validateFieldArea(double farmArea, double fieldArea) {
        if (fieldArea > farmArea / 2) {
            throw new IllegalArgumentException("The field cannot exceed 50% of the total farm area.");
        }
    }

    public void validateTotalFieldArea(double farmArea, double totalFieldArea, double newFieldArea) {
        if (totalFieldArea + newFieldArea > farmArea) {
            throw new IllegalArgumentException("The total area of fields cannot exceed the total farm area.");
        }
    }
}