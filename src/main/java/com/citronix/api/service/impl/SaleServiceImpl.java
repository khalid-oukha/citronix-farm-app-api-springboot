package com.citronix.api.service.impl;

import com.citronix.api.DTO.sale.SaleCreateDto;
import com.citronix.api.domain.Harvest;
import com.citronix.api.domain.Sale;
import com.citronix.api.repository.SaleRepository;
import com.citronix.api.service.HarvestService;
import com.citronix.api.service.SaleService;
import com.citronix.api.web.mapper.SaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final HarvestService harvestService;
    private final SaleMapper saleMapper;


    @Override
    public Sale create(SaleCreateDto saleCreateDto) {
        Harvest harvest = harvestService.findById(saleCreateDto.getHarvestId());
        List<Sale> previousSales = saleRepository.findAllByHarvest(harvest);
        double quantitySaled = totalQuantitySaled(previousSales);

        double remaining = harvest.getTotalQuantity() - quantitySaled;
        if (harvest.getTotalQuantity() - quantitySaled < saleCreateDto.getQuantity()) {
            throw new IllegalArgumentException("There is no available quantity to sale remaining : " + remaining);
        }

        Sale sale = saleMapper.toSale(saleCreateDto);
        sale.setRevenue(sale.getQuantity() * sale.getUnitPrice());
        sale.setHarvest(harvest);
        return saleRepository.save(saleMapper.toSale(saleCreateDto));
    }

    @Override
    public List<Sale> findAllByHarvest(Harvest harvest) {
        return saleRepository.findAllByHarvest(harvest);
    }

    public double totalQuantitySaled(List<Sale> sales) {
        return sales.stream()
                .mapToDouble(Sale::getQuantity)
                .sum();
    }
}
