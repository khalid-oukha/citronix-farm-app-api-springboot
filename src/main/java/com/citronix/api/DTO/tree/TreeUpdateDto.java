package com.citronix.api.DTO.tree;

import com.citronix.api.domain.enums.TreeStatus;
import com.citronix.api.utils.validation.plantingPeriod.ValidPlantingPeriod;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeUpdateDto {

    @ValidPlantingPeriod
    @PastOrPresent(message = "must be in past or present")
    private LocalDateTime plantationDate;

    private TreeStatus status;
}
