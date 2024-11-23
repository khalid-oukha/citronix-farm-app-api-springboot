package com.citronix.api.service.impl;

import com.citronix.api.DTO.sale.SaleCreateDto;
import com.citronix.api.domain.Harvest;
import com.citronix.api.domain.Sale;
import com.citronix.api.repository.SaleRepository;
import com.citronix.api.service.HarvestService;
import com.citronix.api.service.SaleService;
import com.citronix.api.web.exception.EntityNotFoundException;
import com.citronix.api.web.mapper.SaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final HarvestService harvestService;
    private final SaleMapper saleMapper;


    @Override
    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("sale with id : " + id + "not found"));
    }

    @Override
    public Sale create(SaleCreateDto saleCreateDto) {
        Harvest harvest = harvestService.findById(saleCreateDto.getHarvestId());
        validateQuantity(saleCreateDto.getQuantity(), harvest);

        Sale sale = saleMapper.toSale(saleCreateDto);
        sale.setRevenue(sale.getQuantity() * sale.getUnitPrice());
        sale.setHarvest(harvest);
        return saleRepository.save(sale);
    }

    @Override
    public void delete(Long id) {
        Sale sale = findById(id);
        saleRepository.delete(sale);
    }

    @Override
    public Page<Sale> getAllSales(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    public List<Sale> findAllByHarvest(Long id) {
        Harvest harvest = harvestService.findById(id);
        return saleRepository.findAllByHarvest(harvest);
    }

    public double totalQuantitySaled(List<Sale> sales) {
        return sales.stream()
                .mapToDouble(Sale::getQuantity)
                .sum();
    }


    private void validateQuantity(double requestedQuantity, Harvest harvest) {
        List<Sale> previousSales = saleRepository.findAllByHarvest(harvest);
        double quantitySaled = totalQuantitySaled(previousSales);
        double remaining = harvest.getTotalQuantity() - quantitySaled;

        if (remaining < requestedQuantity) {
            throw new IllegalArgumentException(
                    "There is no available quantity to sale. Remaining: " + remaining);
        }
    }

}
