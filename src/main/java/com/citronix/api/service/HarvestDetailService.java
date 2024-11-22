package com.citronix.api.service;

import com.citronix.api.domain.HarvestDetail;

import java.util.List;

public interface HarvestDetailService {
    HarvestDetail create(HarvestDetail harvestDetail);
    
    void createAll(List<HarvestDetail> harvestDetails);
}
