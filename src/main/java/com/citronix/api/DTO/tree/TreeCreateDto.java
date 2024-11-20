package com.citronix.api.DTO.tree;

import com.citronix.api.utils.validation.plantingPeriod.ValidPlantingPeriod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeCreateDto {

    @ValidPlantingPeriod
    @NotNull(message = "plantationDate is required")
    @PastOrPresent(message = "must be in past or present")
    private LocalDateTime plantationDate;

    @NotNull(message = "fieldId is required")
    private Long fieldId;
}
