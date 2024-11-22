package com.citronix.api.service.impl;

import com.citronix.api.domain.HarvestDetail;
import com.citronix.api.repository.HarvestDetailsRepository;
import com.citronix.api.service.HarvestDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HarvestDetailServiceImpl implements HarvestDetailService {

    private final HarvestDetailsRepository harvestDetailRepository;

    @Override
    public HarvestDetail create(HarvestDetail harvestDetail) {
        return harvestDetailRepository.save(harvestDetail);
    }

    @Override
    public void createAll(List<HarvestDetail> harvestDetails) {
        harvestDetailRepository.saveAll(harvestDetails);
    }
}
