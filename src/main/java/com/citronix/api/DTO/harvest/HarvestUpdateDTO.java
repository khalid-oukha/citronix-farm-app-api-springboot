package com.citronix.api.DTO.harvest;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestUpdateDTO {
    private LocalDateTime harvestDate;
}
