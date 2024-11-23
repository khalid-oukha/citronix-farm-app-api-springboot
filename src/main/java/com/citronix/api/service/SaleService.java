package com.citronix.api.service;

import com.citronix.api.DTO.sale.SaleCreateDto;
import com.citronix.api.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleService {
    Sale findById(Long id);

    Sale create(SaleCreateDto saleCreateDto);

    void delete(Long id);

    Page<Sale> getAllSales(Pageable pageable);

//    List<Sale> findAllByHarvest(Harvest harvest);

    Page<Sale> findAll(Pageable pageable);

    List<Sale> findAllByHarvest(Long id);
}
