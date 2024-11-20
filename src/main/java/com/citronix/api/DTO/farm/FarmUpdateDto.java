package com.citronix.api.DTO.farm;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmUpdateDto {
    private String name;
    private String location;

    @Positive(message = "Area must be greater than zero")
    private Double area;
}