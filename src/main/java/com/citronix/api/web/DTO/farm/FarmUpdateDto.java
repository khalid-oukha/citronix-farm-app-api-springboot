package com.citronix.api.web.DTO.farm;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmUpdateDto {
    private String name;
    private String location;
    private Double area;
}