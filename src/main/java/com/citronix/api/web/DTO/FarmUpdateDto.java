package com.citronix.api.web.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmUpdateDto {
    private String name;
    private String location;
    private double area;
}