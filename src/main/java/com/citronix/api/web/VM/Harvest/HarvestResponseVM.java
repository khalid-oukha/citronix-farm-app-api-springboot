package com.citronix.api.web.VM.Harvest;

import com.citronix.api.web.VM.ResponseHarvestDetailVM;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HarvestResponseVM {
    private Long id;
    private LocalDateTime harvestDate;
    private double totalQuantity;
    private List<ResponseHarvestDetailVM> harvestDetails;

}
