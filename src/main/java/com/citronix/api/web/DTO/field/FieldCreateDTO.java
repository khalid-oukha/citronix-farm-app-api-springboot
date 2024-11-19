package com.citronix.api.web.DTO.field;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldCreateDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1000, message = "Area must be at least 1000 m²")
    @NotNull(message = "Area cannot be null")
    private double area;

    @NotNull(message = "farmId cannot be null")
    private Long farmId;
}
