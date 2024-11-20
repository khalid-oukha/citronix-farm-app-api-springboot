package com.citronix.api.utils.validation.plantingPeriod;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlantingPeriodValidator.class)
public @interface ValidPlantingPeriod {
    String message() default "Planting period must be between March and May";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
