package com.citronix.api.web.VM;

import com.citronix.api.domain.Farm;
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
    private Farm farm;
}
