package com.citronix.api.DTO.field;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldUpdateDTO {
    private String name;

    @Positive(message = "Area must be greater than zero")
    private Double area;
}
