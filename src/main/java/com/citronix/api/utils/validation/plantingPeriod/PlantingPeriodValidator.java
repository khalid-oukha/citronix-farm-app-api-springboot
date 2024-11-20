package com.citronix.api.utils.validation.plantingPeriod;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class PlantingPeriodValidator implements ConstraintValidator<ValidPlantingPeriod, LocalDateTime> {
    @Override
    public void initialize(ValidPlantingPeriod constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return true;
        }

        int month = date.getMonthValue();

        return month >= 3 && month <= 5;
    }
}
