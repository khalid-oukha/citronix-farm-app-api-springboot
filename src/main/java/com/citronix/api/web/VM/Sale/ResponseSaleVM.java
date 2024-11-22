package com.citronix.api.web.VM.Sale;

import com.citronix.api.domain.Harvest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSaleVM {
    private Long id;
    private LocalDateTime date;
    private double unitPrice;
    private double quantity;
    private String client;
    private double Revenue;
    private Harvest harvest;
}
