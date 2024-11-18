package com.citronix.api.web.VM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFarmVM {
    private Long id;
    private String name;
    private String location;
    private double area;
    private LocalDateTime createdAt;
}
