package com.citronix.api.DTO.sale;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateDto {
    @NotNull(message = "unitPrice is required")
    @Positive(message = "unitPrice needs to be positive")
    private double unitPrice;

    @NotNull(message = "quantity is required")
    @Positive(message = "quantity needs to be positive")
    private double quantity;

    @NotBlank(message = "client is required")
    private String client;

    @NotNull(message = "harvestId is required")
    private Long harvestId;
}
