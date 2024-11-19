package com.citronix.api.web.DTO.field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldCreateDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Area must be greater than zero")
    @NotNull(message = "Area cannot be null")
    private double area;

    @NotNull(message = "farmId cannot be null")
    private Long farmId;
}
