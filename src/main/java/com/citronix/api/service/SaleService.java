package com.citronix.api.service;

import com.citronix.api.DTO.sale.SaleCreateDto;
import com.citronix.api.domain.Harvest;
import com.citronix.api.domain.Sale;

import java.util.List;

public interface SaleService {
    Sale findById(Long id);

    Sale create(SaleCreateDto saleCreateDto);

    List<Sale> findAllByHarvest(Harvest harvest);
}
