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
public class ResponseHarvestDetailVM {
    private Long id;
    private double quantity;
    private Long treeId;
    private LocalDateTime treePlantationDate;
}