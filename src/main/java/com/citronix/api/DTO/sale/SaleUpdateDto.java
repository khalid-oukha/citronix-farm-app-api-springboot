package com.citronix.api.DTO.sale;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleUpdateDto {
    @Positive(message = "unitPrice needs to be positive")
    private Double unitPrice;

    @Positive(message = "quantity needs to be positive")
    private Double quantity;

    private String client;
}
