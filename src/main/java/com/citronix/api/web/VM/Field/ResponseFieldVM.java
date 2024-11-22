package com.citronix.api.web.VM.Field;

import com.citronix.api.web.VM.Farm.ResponseFarmVM;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFieldVM {
    private Long id;
    private String name;
    private double area;
    private ResponseFarmVM farm;
}
