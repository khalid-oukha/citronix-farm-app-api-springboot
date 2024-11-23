package com.citronix.api.web.rest;

import com.citronix.api.DTO.sale.SaleCreateDto;
import com.citronix.api.domain.Sale;
import com.citronix.api.service.SaleService;
import com.citronix.api.web.VM.Sale.ResponseSaleVM;
import com.citronix.api.web.mapper.SaleMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SaleController {

    private final SaleService saleService;
    private final SaleMapper saleMapper;

    @PostMapping("/sales")
    public ResponseEntity<ResponseSaleVM> create(@Valid @RequestBody SaleCreateDto saleCreateDto) {
        Sale sale = saleService.create(saleCreateDto);
        ResponseSaleVM response = saleMapper.toResponse(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<ResponseSaleVM> findById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        ResponseSaleVM response = saleMapper.toResponse(sale);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
