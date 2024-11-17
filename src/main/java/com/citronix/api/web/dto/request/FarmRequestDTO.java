package com.citronix.api.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FarmRequestDTO{

         @NotBlank(message = "Name is required")
         private String name;

         @NotBlank(message = "location is required")
         private String location;

         @Positive(message = "Area must be greater than zero")
         @NotNull(message = "Area cannot be null")
         private double area;

         private LocalDateTime createdAt = LocalDateTime.now();
}
