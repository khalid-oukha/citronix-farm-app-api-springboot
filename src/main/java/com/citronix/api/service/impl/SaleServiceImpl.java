package com.citronix.api.service.impl;

import com.citronix.api.repository.SaleRepository;
import com.citronix.api.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;

}
